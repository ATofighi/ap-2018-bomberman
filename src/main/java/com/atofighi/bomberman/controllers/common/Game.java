package com.atofighi.bomberman.controllers.common;

import com.atofighi.bomberman.Client;
import com.atofighi.bomberman.Server;
import com.atofighi.bomberman.addons.MonsterAddon;
import com.atofighi.bomberman.addons.MonsterAddonInterface;
import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.controllers.client.GameKeyListener;
import com.atofighi.bomberman.controllers.client.InGameMenu;
import com.atofighi.bomberman.controllers.client.PaintController;
import com.atofighi.bomberman.controllers.common.units.*;
import com.atofighi.bomberman.models.Map;
import com.atofighi.bomberman.models.units.*;
import com.atofighi.bomberman.util.*;
import com.atofighi.bomberman.views.client.game.*;
//import ir.atofighi.bomberman.BallMonster;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.atofighi.bomberman.configs.BoardConfiguration.boardHeight;
import static com.atofighi.bomberman.configs.BoardConfiguration.boardWidth;
import static com.atofighi.bomberman.configs.BoardConfiguration.cellSize;

public class Game {

    public enum Type {
        ALONE_GAME, MULTI_PLAYER_SERVER, MULTI_PLAYER_CLIENT;
    }

    private Type type;
    private static HashMap<String, Game> games = new HashMap<>();
    private static int currentTime = 0;
    private Client app = null;
    private Server server;
    private GamePanel gamePanel = new GamePanel();
    private BoardPanel boardPanel = new BoardPanel();
    private StatusPanel statusPanel = new StatusPanel(this);
    private ChatPanel chatPanel = new ChatPanel();
    private MapController mapController;
    private Map map = null;
    private GameHandle gameHandle;
    private GameTimerThread gameTimerThread;
    private InGameMenu inGameMenu;
    private LosePainter losePainter;
    private PaintController painter;
    private int myBombermanId = -1;
    private int myLocationX, myLocationY;
    private int startTime;
    private boolean alive = true;
    private Game nextGame = null;
    private Direction mapViewMoveDirection = null;
    public static final Object globalLock = new Object();
    public static final Object unitsLock = new Object();
    private static final List<MonsterPackage> availableMonsters = new ArrayList<>(0);
    private StringBuffer chat = new StringBuffer();

    private boolean paused = false;
    private int bombermanLimit = 1;
    private float numberOfMonsters;
    private String id;

    private Game() {
        id = new RandomString(64).nextString();
        this.startTime = Game.getCurrentTime();
        games.put(id, this);
    }

    private Game(Client app) {
        this();
        type = Type.ALONE_GAME;
        this.app = app;
    }

    private Game(Server app) {
        this();
        type = Type.MULTI_PLAYER_SERVER;
        this.server = app;
    }

    public Game(Client app, int width, int height, float numberOfMonsters) {
        this(app, width, height, 1, true, true, numberOfMonsters);
    }


    private void _init(int width, int height, int level, boolean newBomberman, boolean clearTime, float numberOfMonsters) {
        this.numberOfMonsters = numberOfMonsters;
        if (clearTime) {
            Game.setCurrentTime(0);
        }
        this.startTime = Game.getCurrentTime();
        mapController = new MapController(new Map(id, level, width + 2, height + 2));
        mapController.addRandomWalls();
        mapController.addRandomMonsters(numberOfMonsters);
        if (newBomberman) {
            newBomberman("AliReza");
        }
        mapController.addDoor();
        mapController.addPowerUps();
    }

    public Game(Client app, int width, int height, int level, boolean newBomberman, boolean clearTime, float numberOfMonsters) {
        this(app);
        _init(width, height, level, newBomberman, clearTime, numberOfMonsters);
    }

    public Game(Server server, int width, int height, int level, float numberOfMonsters, int bombermanLimit) {
        this(server);
        this.bombermanLimit = bombermanLimit;
        _init(width, height, level, false, false, numberOfMonsters);
    }

    public Game(Client app, Map map) {
        this(app, map, true);
    }

    public Game(Client app, Map map, boolean alone) {
        this(app);
        setCurrentTime(map.getCurrentTime());
        Unit.setAutoIncrementId(map.getUnitsAutoIncrementId());
        if (!alone) {
            this.map = map;
            type = Type.MULTI_PLAYER_CLIENT;
        } else {
            mapController = new MapController(map);
        }
        numberOfMonsters = map.getMonsters().size();
        id = map.getId();
    }

    public static synchronized int getCurrentTime() {
        return currentTime;
    }

    public static synchronized void setCurrentTime(int currentTime) {
        Game.currentTime = currentTime;
    }

    public static Game getGame(String content) {
        return games.get(content);
    }

    public static List<GameStatus> getAliveGames() {
        List<GameStatus> list = new ArrayList<>();
        games.forEach((id, game) -> {
            if (game.isAlive()) {
                list.add(new GameStatus(id, Game.getCurrentTime() - game.startTime,
                        game.getMap().getBombermans().size(), game.bombermanLimit));
            }
        });
        return list;

    }


    public BombermanController me() {
        if (type == Type.ALONE_GAME) {
            if (mapController.getBombermans().size() > 0) {
                return mapController.getBombermans().get(0);
            }
        }
        return null;
    }

    public Bomberman myBomberman() {
        if (me() != null) {
            return me().get();
        }
        if (type == Type.MULTI_PLAYER_CLIENT && myBombermanId >= 0) {
            Optional<Bomberman> bomberman = map.getBombermans().stream().filter((b) -> b.getId() == myBombermanId)
                    .findFirst();
            if (bomberman.isPresent()) {
                return bomberman.get();
            }
        }
        return null;
    }

    public void run() {
        if (type == Type.ALONE_GAME || type == Type.MULTI_PLAYER_CLIENT) {
            inGameMenu = new InGameMenu(this);
            gamePanel.add(boardPanel, BorderLayout.CENTER);
            LeftPanel leftPanel = new LeftPanel();
            gamePanel.add(leftPanel, BorderLayout.LINE_START);
            leftPanel.add(statusPanel);
            leftPanel.add(chatPanel, BorderLayout.SOUTH);
            boardPanel.setGame(this);
            boardPanel.setBoardBounds(showX(), showY());
            losePainter = new LosePainter(getMap());
            app.getMainFrame().setPanel(gamePanel);

            gamePanel.addKeyListener(new GameKeyListener(this));
            gamePanel.requestFocus();

            chatPanel.getTextField().addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_TAB:
                        case KeyEvent.VK_UP:
                        case KeyEvent.VK_DOWN:
                        case KeyEvent.VK_LEFT:
                        case KeyEvent.VK_RIGHT:
                            gamePanel.requestFocus();
                            e.consume();
                            break;
                        case KeyEvent.VK_ENTER:
                            addChat(myBomberman(), chatPanel.getTextField().getText());
                            chatPanel.getTextField().setText("");
                            gamePanel.requestFocus();
                            break;
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
        }

        gameHandle = new GameHandle(this);
        if (type == Type.ALONE_GAME || type == Type.MULTI_PLAYER_CLIENT ||
                (type == Type.MULTI_PLAYER_SERVER && games.size() == 1)) {
            gameTimerThread = new GameTimerThread(this);
            gameTimerThread.start();
        }

        gameHandle.start();
    }

    public int showX() {
        if (myBomberman() != null) {
            return myBomberman().getLegX();
        } else {
            return myLocationX;
        }
    }

    public int showY() {
        if (myBomberman() != null) {
            return myBomberman().getLegY();
        } else {
            return myLocationY;
        }
    }

    public Map getMap() {
        if (type == Type.MULTI_PLAYER_CLIENT) {
            return map;
        }
        return mapController.getMap();
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public synchronized boolean isPaused() {
        return type == Type.ALONE_GAME && (inGameMenu.isShowMenu() || paused);
    }

    public synchronized void setPaused(boolean paused) {
        this.paused = paused;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public MapController getMapController() {
        return mapController;
    }

    public StatusPanel getStatusPanel() {
        return statusPanel;
    }

    public InGameMenu getInGameMenu() {
        return inGameMenu;
    }

    public Client getApp() {
        return app;
    }

    public void cheat(String cheatString) {
        try {
            if (type == Type.ALONE_GAME) {
                System.out.println("trying to cheat: " + cheatString);
                switch (cheatString) {
                    case "bomb bede":
                        myBomberman().setBombLimit(1000);
                        break;
                    case "gondash kon":
                        myBomberman().setBombRange(1000);
                        break;
                    case "ghost":
                        myBomberman().setGhost(true);
                        break;
                    case "shield":
                        myBomberman().setLastShieldTime(Game.getCurrentTime());
                        break;
                    case "control":
                        myBomberman().setControlBombs(true);
                        break;
                    case "no wall":
                        for (WallController wallController : mapController.getWalls()) {
                            wallController.destroy(myBomberman());
                        }
                }


                System.err.println(cheatString.substring(0, 8));
                if (cheatString.substring(0, 8).equals("move to ")) {
                    int x = Integer.parseInt(cheatString.split(" ")[2]);
                    int y = Integer.parseInt(cheatString.split(" ")[3]);
                    mapController.get(myBomberman().getX(), myBomberman().getY()).remove(me());
                    myBomberman().setRealX((x + 1) * BoardConfiguration.cellSize);
                    myBomberman().setRealY((y + 1) * BoardConfiguration.cellSize);
                    mapController.get(myBomberman().getX(), myBomberman().getY()).add(me());
                }
            }
        } catch (Exception e) {

        }
    }

    public void lose() {
        if (type != Type.MULTI_PLAYER_SERVER) {
            losePainter.setLosed(true);
        }
    }

    public void win() {
        //app.setRepaintLoop(false);
        new Thread(() -> {
            int width = getMap().getWidth() - 2;
            int height = getMap().getHeight() - 2;
            int level = getMap().getLevel() + 1;

            if (type == Type.ALONE_GAME || type == Type.MULTI_PLAYER_SERVER) {
                for (BombermanController bombermanController : mapController.getBombermans()) {
                    bombermanController.refresh();
                }
                close();


                if (level > 4) {
                    numberOfMonsters += 0.05f * numberOfMonsters;
                }
                if (type == Type.ALONE_GAME) {
                    nextGame = new Game(app, width, height, level, false, false, numberOfMonsters);
                } else {
                    nextGame = new Game(server, width, height, level, numberOfMonsters, bombermanLimit);
                }
                for (BombermanController bombermanController : mapController.getBombermans()) {
                    nextGame.getMapController().addBomberman(bombermanController.get());
                }
                if (type == Type.ALONE_GAME) {
                    app.newGame(nextGame);
                } else if (type == Type.MULTI_PLAYER_SERVER) {
                    server.newGame(nextGame);
                }
                //app.setRepaintLoop(true);
            }
        }).start();
    }


    public void close() {
        synchronized (Game.globalLock) {
            gameHandle.interrupt();
            if (type == Type.ALONE_GAME) {
                gameTimerThread.interrupt();
            }
            if (mapController != null) {
                for (BombermanController bomberman : mapController.getBombermans()) {
                    if (bomberman.getRefreshTimer() != null) {
                        bomberman.getRefreshTimer().interrupt();
                    }
                }
            }
            alive = false;
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "app=" + app +
                ", gamePanel=" + gamePanel +
                ", boardPanel=" + boardPanel +
                ", statusPanel=" + statusPanel +
                ", mapController=" + mapController +
                ", gameHandle=" + gameHandle +
                ", gameTimerThread=" + gameTimerThread +
                ", inGameMenu=" + inGameMenu +
                ", losePainter=" + losePainter +
                ", paused=" + paused +
                '}';
    }

    public static List<MonsterPackage> getAvailableMonsters() {
        return availableMonsters;
    }

    public static void addAvailableMonsters(MonsterPackage monsterPackage) {
        availableMonsters.add(monsterPackage);
    }

    public static void addAvailableMonsters(MonsterAddonInterface monsterPackage) {
        availableMonsters.add(new MonsterAddon(monsterPackage));
    }

    public static void addAvailableMonsters(MonsterPackageClass data) throws Exception {
        Base64ClassLoader classLoader = new Base64ClassLoader(Game.class.getClassLoader());
        Class monsterPackageClass = classLoader.loadClass(data.getName(), data.getContent());
        MonsterAddonInterface monsterPackage = (MonsterAddonInterface) monsterPackageClass.newInstance();
        Game.addAvailableMonsters(monsterPackage);

    }

    static {
        addAvailableMonsters(new MonsterPackage(Blacki.class, BlackiController.class));
        addAvailableMonsters(new MonsterPackage(Bluei.class, BlueiOrangiPurpeliController.class));
        addAvailableMonsters(new MonsterPackage(Orangi.class, BlueiOrangiPurpeliController.class));
        addAvailableMonsters(new MonsterPackage(Purpeli.class, BlueiOrangiPurpeliController.class));
//        addAvailableMonsters(new BallMonster());
    }

    public int getBombermanLimit() {
        return bombermanLimit;
    }

    public void setBombermanLimit(int bombermanLimit) {
        this.bombermanLimit = bombermanLimit;
    }

    public Bomberman newBomberman(String name) {
        synchronized (Game.unitsLock) {
            int x, y;
            if (bombermanLimit == 1) {
                x = y = 1;
            } else {
                Cell freeCell = mapController.getFreeCells().get(0);
                x = freeCell.x;
                y = freeCell.y;
            }
            Bomberman bomberman = new Bomberman(name, x, y);
            bomberman.setLastShieldTime(Game.currentTime);
            mapController.addBomberman(bomberman);
            return bomberman;
        }
    }

    public int getStartTime() {
        return startTime;
    }

    public enum Action {
        PRESS_UP, PRESS_RIGHT, PRESS_DOWN, PRESS_LEFT,
        RELEASE_UP, RELEASE_RIGHT, RELEASE_DOWN, RELEASE_LEFT,
        ADD_BOMB, CONTROL;

        public Direction getDirection() {
            switch (this) {
                case PRESS_DOWN:
                case RELEASE_DOWN:
                    return Direction.DOWN;
                case PRESS_LEFT:
                case RELEASE_LEFT:
                    return Direction.LEFT;
                case RELEASE_RIGHT:
                case PRESS_RIGHT:
                    return Direction.RIGHT;
                case PRESS_UP:
                case RELEASE_UP:
                    return Direction.UP;
                default:
                    return null;
            }
        }

        public static Action press(Direction direction) {
            return valueOf("PRESS_" + direction.name());
        }

        public static Action release(Direction direction) {
            return valueOf("RELEASE_" + direction.name());
        }

        public boolean isPress() {
            return this == PRESS_UP || this == PRESS_RIGHT || this == PRESS_DOWN
                    || this == PRESS_LEFT;
        }

        public boolean isRelease() {
            return this == RELEASE_UP || this == RELEASE_RIGHT || this == RELEASE_DOWN
                    || this == RELEASE_LEFT;
        }
    }

    public void performAction(Bomberman bomberman, Action action) {
        if (!isPaused()) {
            if (type == Type.ALONE_GAME || type == Type.MULTI_PLAYER_SERVER) {
                Optional<BombermanController> bombermanControllerOptional = mapController.getBombermans().stream().filter(c ->
                        c.get() == bomberman).findAny();
                if (bombermanControllerOptional.isPresent()) {
                    BombermanController bombermanController = bombermanControllerOptional.get();
                    switch (action) {
                        case ADD_BOMB:
                            bombermanController.addBomb();
                            break;
                        case CONTROL:
                            bombermanController.destroyBomb();
                            break;
                        case RELEASE_DOWN:
                        case RELEASE_UP:
                        case RELEASE_LEFT:
                        case RELEASE_RIGHT:
                            bomberman.setInMove(false);
                            break;
                        case PRESS_DOWN:
                        case PRESS_UP:
                        case PRESS_LEFT:
                        case PRESS_RIGHT:
                            bomberman.setDirection(action.getDirection());
                            bomberman.setInMove(true);
                            break;
                    }
                }
            } else {
                if (!action.isPress() || !bomberman.isInMove() ||
                        bomberman.getDirection() != action.getDirection()) {
                    app.getProtocol().doAction(action);
                }
            }
        }
    }

    public String getId() {
        return id;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Type getType() {
        return type;
    }

    public PaintController getPainter() {
        //return painter;
        synchronized (unitsLock) {
            return new PaintController(getMap());
        }
    }

    public LosePainter getLosePainter() {
        return losePainter;
    }

    public int getMyBombermanId() {
        return myBombermanId;
    }

    public void setMyBombermanId(int myBombermanId) {
        this.myBombermanId = myBombermanId;
    }

    public int getMyLocationX() {
        return myLocationX;
    }

    public void setMyLocationX(int myLocationX) {
        int maxWidth = boardWidth * cellSize;
        int currentWidth = Math.max(boardWidth, getMap().getWidth()) * cellSize;
        this.myLocationX = Math.min(Math.max(maxWidth / 2, myLocationX), currentWidth - maxWidth / 2);
    }

    public int getMyLocationY() {
        return myLocationY;
    }

    public void setMyLocationY(int myLocationY) {
        int maxHeight = boardHeight * cellSize;
        int currentHeight = Math.max(boardHeight, getMap().getHeight()) * cellSize;
        this.myLocationY = Math.min(Math.max(maxHeight / 2, myLocationY), currentHeight - maxHeight / 2);
    }

    public Direction getMapViewMoveDirection() {
        return mapViewMoveDirection;
    }

    public void setMapViewMoveDirection(Direction mapViewMoveDirection) {
        this.mapViewMoveDirection = mapViewMoveDirection;
    }

    public Game getNextGame() {
        return nextGame;
    }

    public String getChats() {
        return chat.toString();
    }

    public void addChat(Bomberman owner, String message) {
        if (type == Type.MULTI_PLAYER_SERVER || type == Type.ALONE_GAME) {
            chat.append(owner.getName()).append(": ").append(message).append("\n");
        } else if (type == Type.MULTI_PLAYER_CLIENT) {
            if (owner != null) {
                app.getProtocol().sendChat(message);
            } else {
                // nothing
            }
        }
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }
}
