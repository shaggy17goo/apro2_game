package Client.TUI;

/**
 * Text User Interface
 * This is the controller and viewer from the MVC model together.
 * Viewer is implemented inthe Printerthread class.
 * Controller is implemented in the InputThread class.
 * @author mpanka
 */
public class TUI {

    public static final String ANSI_RESET   = "\u001B[0m";
    public static final String ANSI_BLACK   = "\u001B[30m";
    public static final String ANSI_RED     = "\u001B[31m";
    public static final String ANSI_GREEN   = "\u001B[32m";
    public static final String ANSI_YELLOW  = "\u001B[33m";
    public static final String ANSI_BLUE    = "\u001B[34m";
    public static final String ANSI_PURPLE  = "\u001B[35m";
    public static final String ANSI_CYAN    = "\u001B[36m";
    public static final String ANSI_WHITE   = "\u001B[37m";

    private final int WARRIOR_IDX       = 0;
    private final int WIZARD_IDX        = 1;
    private final int ARCHER_IDX        = 2;
    private final int PRIEST_IDX        = 3;
    private final int NECROMANCER_IDX   = 4;
    private final int PALADIN_IDX       = 5;

   /* int X, Y;
    private String[][] boardBuffer; // 2-char string – looks good in console: 2 chars per square
    private volatile GameEngine gameMap;
    private HeroTable heroTable = new HeroTable();
    private PrinterThread printer = new PrinterThread();
    private InputThread input = new InputThread();
    private EngineThread engine = new EngineThread();
    private volatile boolean runPrinter = false;   // the printing thread is running
    private volatile boolean runEngine = false;   // the engine thread is running
    private volatile boolean processInput = false;   // there are data to process by the engine
    private volatile boolean repaint = false;   // should the printing thread paint ?
    private volatile char ch = 's';
    private Player player;
    private AtomicReference<String> actionRequest;  // from input thread to engine thread - threadsafe


    public TUI() { }

    public int connectLocalMap(GameEngine gm) {
        gameMap = gm;
        Y = gm.getGameMap().getMaxY();
        X = gm.getGameMap().getMaxY();
        boardBuffer = new String[2*Y][X]; // console: print 2x5 box per field
        actionRequest = new AtomicReference<>("");  // empty string
        return 0;
    }

    public void setPlayer(Player p) {
        if (p == null) {
            System.err.println("[TUI]: player : null");
            return;
        }
        player = p;
    }

    public void addHero(Hero h) {
        if (h == null)
            System.out.println("[TUI]: hero : null - this hero will be deleted!");
        gameMap.addHero(h);
        if ( h.getClass().equals(Warrior.class) ) {
            heroTable.setHero(0, WARRIOR_IDX, h);
        } else if ( h.getClass().equals(Wizard.class) ) {
            heroTable.setHero(0, WIZARD_IDX, h);
        } else if ( h.getClass().equals(Archer.class) ) {
            heroTable.setHero(0, ARCHER_IDX, h);
        } else if ( h.getClass().equals(Priest.class) ) {
            heroTable.setHero(0, PRIEST_IDX, h);
        } else if ( h.getClass().equals(Necromancer.class) ) {
            heroTable.setHero(0, NECROMANCER_IDX, h);
        } else if ( h.getClass().equals(Paladin.class) ) {
            heroTable.setHero(0, PALADIN_IDX, h);
        }
    }

    public void go() {
        if (gameMap == null) {
            System.err.println("[TUI: go]: no map!");
            return;
        }
        runPrinter = true;
        runEngine = true;
        printer.start();
        input.start();
        engine.start();
    }



    private class PrinterThread extends Thread {
        @Override
        public void run() {
            System.out.println("printer thread running...");
            while (runPrinter) {
                if (!repaint) {
                    try { sleep(500); } catch (InterruptedException e) { }
                    continue;
                }
                // if repaint == true, execute the drawing routine:
                repaint = false;    // this ensures the following iteration of while() doesn't repaint

                populateBuffer();
                // clear the screen:
                // FIXME:
                //try { Runtime.getRuntime().exec("clear"); } catch (IOException e) { e.printStackTrace(); }

                printBoard();

                try {
//                    System.out.println("printer wating...");
                    sleep(500);
                } catch (InterruptedException e) { }
            }
            System.out.println("printer thread exiting...");

        }

        private void populateBuffer() {
            for (int y = 0; y < Y; ++y) {   // populate buffer according to the state of game board
                for (int x = 0; x < X; ++x) {
                    boardBuffer[y*2][x] =   "┌────";
                    boardBuffer[y*2+1][x] = "│ " + gameMap.fieldAt(y,x) + " ";  // GameMap.fieldAt().toString()
                }
            }
        }

        private void printBoard() {
            // Print first row with column numbers:
            AtomicInteger k = new AtomicInteger(0);
            System.out.print('\t');
            *//*Arrays.stream(boardBuffer[0])
                    .forEach(s -> System.out.print(String.format("%4d", k.getAndIncrement()) + " ") );
            System.out.println();
            // Print boxes. Two lines of text per box. Begin with row number
            for (int y = 0; y < Y; ++y) {   // two lines per Field
                System.out.println( '\t' + String.join("", boardBuffer[2*y]) + "│" );
                System.out.println( " " + y + '\t' + String.join("", boardBuffer[2*y+1]) + "│" );
            }
            // Print last line to close off the bottom of board
            System.out.print('\t');
            Arrays.stream(boardBuffer[0])
                    .forEach(s -> System.out.print("─────") );
            System.out.println();*//*

        }


    }



    private class InputThread extends Thread {
        @Override
        public void run() {
            System.out.println("input thread running...\n[input]: q - exit, r - repaint");
            while(ch != 'q') {
                try { ch = (char)System.in.read(); } catch (IOException e) { e.printStackTrace(); }
                switch (ch) {
                    case 'r' :  repaint = true;          break;
                    case 'a' :  promptAction();          break;
                    case 'q' :  runPrinter = false;
                                runEngine = false;      break;
                }
            }
            System.out.println("input thread exiting...");
        }

        private void promptAction() {
            try { sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println("[Action input]: format (player number)#(hero numnber)#(action number)#(target y)x(target x)");
            System.out.print("[Action input]: ");
            Scanner sc = new Scanner(System.in);
            String actionString = sc.next();
            actionRequest.set(actionString);
            processInput = true;    // this notifies engine thread of new input being ready
            System.out.println("[Action input]: action request sent : " + actionRequest);
        }

    }



    private class EngineThread extends Thread {

//        private volatile String actionRequest;
        private final int PLAYER_NUM_IDX = 0;   // my local player
        private final int HERO_NUM_IDX = 1;
        private final int ACTION_NUM_IDX = 2;
        private final int TARGET_Y_IDX = 3;
        private final int TARGET_X_IDX = 4;
        private int action_idx;
        private int targetY;
        private int targetX;
        private int player_idx;
        private int hero_idx;
        private String regex;

        public EngineThread() { }


        @Override
        public void run() {
            System.out.println("engine thread running...");
            System.err.println("Y: " + Y + ", X: " + X);
            *//*
             * FIXME this regex doesnt work properly
             * player no, hero no, action no, pos y, pos x
             * just for now
             *//*
            regex = "[0-3]#[0-6]#[0-7]#[0-" +
                    (int)(Y-1)/10 + "0-" + (int)(Y-1)%10 + "]x[0-" + (int)(X-1)/10 + "0-" + (int)(X-1)%10 + "]";
            System.err.println("regex: " + regex);
            while (runEngine) {
                if (processInput) {
                    processInput = false;
                    parseRequest();
                }
                try { sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            }
            System.out.println("engine thread exiting...");
        }

        public boolean parseRequest() {
//            if (!actionRequest.toString().matches(regex)) {  // looks like incorrect request
//                System.err.println("[Engine thread]: bad request! " + actionRequest);
//                return false;
//            }
            System.out.println("[Engine thread]: processing request " + actionRequest + " ...");
            String[] params = actionRequest.toString().split("[#x]");
            action_idx = Integer.parseInt(params[ACTION_NUM_IDX]);
            targetY = Integer.parseInt(params[TARGET_Y_IDX]);
            targetX = Integer.parseInt(params[TARGET_X_IDX]);
            player_idx = Integer.parseInt(params[PLAYER_NUM_IDX]);
            hero_idx = Integer.parseInt(params[HERO_NUM_IDX]);
            switch (action_idx) { // which action are we going to perform?
// FIXME
                case 0 :        // move - for example
                    System.out.println("[Engine]: moving: player: "+player_idx+", hero: "+hero_idx+", Y: "+targetY+", X:"+targetX);
                    gameMap.changePosition(heroTable.getHero(player_idx, hero_idx), targetY, targetX);
                    break;
            }

            return true;
        }

*/

}
