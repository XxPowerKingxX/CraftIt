package me.thypthon;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @version 1.0
 * @author Laubi
 * @lastchance 14.08.2011 - 23:07
 */
public enum Items{
    // Blocks
    AIR                     (0,(byte) 0, "Air"),
    STONE                   (1,(byte) 0, "Stone",  "rock"),
    GRASS                   (2,(byte) 0, "Grass"),
    DIRT                    (3,(byte) 0, "Dirt"),
    COBBLESTONE             (4,(byte) 0, "Cobblestone", "cobble"),
    WOOD                    (5,(byte) 0, "Wood", "woodplank", "plank", "woodplanks", "planks"),
    SAPLING                 (6,(byte) 0, "Sapling",  "seedling"),
    SAPLING_REDWOOD         (6,(byte) 1, "Redwood Sapling", "redwoodsapling"),
    SAPLING_BIRCH           (6,(byte) 2, "Birch Sapling", "birchsapling"),
    BEDROCK                 (7,(byte) 0, "Bedrock", "adminium", "bedrock"),
    WATER                   (8,(byte) 0, "Water", "watermoving", "movingwater", "flowingwater", "waterflowing"),
    STATIONARY_WATER        (9,(byte) 0, "Water         (stationary)", "water", "waterstationary", "stationarywater", "stillwater"),
    LAVA                    (10,(byte) 0, "Lava", "lavamoving", "movinglava", "flowinglava", "lavaflowing"),
    STATIONARY_LAVA         (11,(byte) 0, "Lava         (stationary)", "lava", "lavastationary", "stationarylava", "stilllava"),
    SAND                    (12,(byte) 0, "Sand"),
    GRAVEL                  (13,(byte) 0, "Gravel"),
    GOLD_ORE                (14,(byte) 0, "Gold ore", "goldore"),
    IRON_ORE                (15,(byte) 0, "Iron ore", "ironore"),
    COAL_ORE                (16,(byte) 0, "Coal ore", "coalore"),
    LOG                     (17,(byte) 0, "Log", "tree", "pine", "oak"),
    LOG_REDWOOD             (17,(byte) 1, "redwood"),
    LOG_BIRCH               (17,(byte) 2, "birch"),
    LEAVES                  (18,(byte) 0, "Leaves","leaf"),
    SPONGE                  (19,(byte) 0, "Sponge"),
    GLASS                   (20,(byte) 0, "Glass"),
    LAPIS_LAZULI_ORE        (21,(byte) 0, "Lapis lazuli ore", "lapislazuliore", "blueore", "lapisore"),
    LAPIS_LAZULI            (22,(byte) 0, "Lapis lazuli", "lapislazuli", "lapislazuliblock", "bluerock"),
    DISPENSER               (23,(byte) 0, "Dispenser"),
    SANDSTONE               (24,(byte) 0, "Sandstone"),
    NOTE_BLOCK              (25,(byte) 0, "Note block", "musicblock", "noteblock", "note", "music", "instrument"),
    BED                     (26,(byte) 0, "Bed"),
    POWERED_RAIL            (27,(byte) 0, "Powered Rail", "poweredrail", "boosterrail", "poweredtrack", "boostertrack", "booster"),
    DETECTOR_RAIL           (28,(byte) 0, "Detector Rail", "detectorrail", "detector"),
    PISTON_STICKY_BASE      (29,(byte) 0, "Sticky Piston", "stickypiston"),
    WEB                     (30,(byte) 0, "Web", "spiderweb"),
    LONG_GRASS              (31,(byte) 0, "Long grass", "longgrass", "tallgrass"),
    DEAD_BUSH               (32,(byte) 0, "Shrub", "deadbush", "shrub", "deadshrub", "tumbleweed"),    
    PISTON_BASE             (33,(byte) 0, "Piston", "piston"),
    PISTON_EXTENSION        (34,(byte) 0, "Piston extension", "pistonhead"),
    WOOL_WHITE              (35,(byte) 0, "White Wool", "White cloth", "White","Wool", "cloth"),
    WOOL_ORANGE             (35,(byte) 1, "Orange Wool", "orange cloth", "Orange"),
    WOOL_MAGENTA            (35,(byte) 2, "Magenta Wool", "Magenta cloth", "Magenta"),
    WOOL_LIGHTBLUE          (35,(byte) 3, "Lightblue Wool", "Lightblue cloth", "Lightblue"),
    WOOL_YELLOW             (35,(byte) 4, "Yellow Wool", "Yellow cloth", "Yellow"),
    WOOL_LIME               (35,(byte) 5, "Lime Wool", "Lime cloth", "Lime"),
    WOOL_PINK               (35,(byte) 6, "Pink Wool", "Pink cloth", "Pink"),
    WOOL_GRAY               (35,(byte) 7, "Gray Wool", "Gray cloth", "Gray"),
    WOOL_LIGHTGRAY          (35,(byte) 8, "Lightgray Wool", "Lightgray cloth", "Lightgray"),
    WOOL_CYAN               (35,(byte) 9, "Cyan Wool", "Cyan cloth", "Cyan"),
    WOOL_PURPLE             (35,(byte) 10, "Purple Wool", "Purple cloth", "Purple"),
    WOOL_BLUE               (35,(byte) 11, "Blue Wool", "Blue cloth", "Blue"),
    WOOL_BROWN              (35,(byte) 12, "Brown Wool", "Brown cloth", "Brown"),
    WOOL_GREEN              (35,(byte) 13, "Green Wool", "Green cloth", "Green"),
    WOOL_RED                (35,(byte) 14, "Red Wool", "Red cloth", "Red"),
    WOOL_BLACK              (35,(byte) 15, "Black Wool", "Black cloth", "Black"),
    PISTON_MOVING_PIECE     (36,(byte) 0, "Piston moving piece", "movingpiston"),
    YELLOW_FLOWER           (37,(byte) 0, "Yellow flower", "yellowflower", "flower"),
    RED_FLOWER              (38,(byte) 0, "Red rose", "redflower", "redrose", "rose"),
    BROWN_MUSHROOM          (39,(byte) 0, "Brown mushroom", "brownmushroom", "mushroom"),
    RED_MUSHROOM            (40,(byte) 0, "Red mushroom", "redmushroom"),
    GOLD_BLOCK              (41,(byte) 0, "Gold block", "gold", "goldblock"),
    IRON_BLOCK              (42,(byte) 0, "Iron block", "iron", "ironblock"),
    DOUBLE_STEP_STONE       (43,(byte) 0, "Double stone step", "doubleslab","doublestoneslab",  "doublestep","doublestonestep","Double step"),
    DOUBLE_STEP_SANDSTONE   (43,(byte) 1, "Double sandstone step", "doublesandstoneslab",  "doublesandstonestep"),
    DOUBLE_STEP_WOOD        (43,(byte) 2, "Double wood step", "doublewoodslab",  "doublewoodstep"),
    DOUBLE_STEP_COBBLE      (43,(byte) 3, "Double cobble step", "doublecobbleslab",  "doublecobblestep"),
    STEP_STONE              (44,(byte) 0, "Stone Step",  "stoneslab", "stonestep", "stonehalfstep", "Step", "slab", "stoneslab", "step", "halfstep"),
    STEP_SANDSTONE          (44,(byte) 1, "Sandstone Step",  "sandstoneslab", "sandstonestep", "sandstonehalfstep"),
    STEP_WOOD               (44,(byte) 2, "Wood Step",  "woodslab", "woodstep", "woodhalfstep"),
    STEP_COBBLE             (44,(byte) 3, "Cobble Step",  "cobbleslab", "cobblestep", "cobblehalfstep"),
    BRICK                   (45,(byte) 0, "Brick", "brick", "brickblock"),
    TNT                     (46,(byte) 0, "TNT", "tnt", "c4", "explosive"),
    BOOKCASE                (47,(byte) 0, "Bookcase", "bookshelf", "bookshelves", "bookcase", "bookcases"),
    MOSSY_COBBLESTONE       (48,(byte) 0, "Cobblestone         (mossy)", "mossycobblestone", "mossstone", "mossystone", "mosscobble", "mossycobble", "moss", "mossy", "sossymobblecone"),
    OBSIDIAN                (49,(byte) 0, "Obsidian", "obsidian"),
    TORCH                   (50,(byte) 0, "Torch", "torch", "light", "candle"),
    FIRE                    (51,(byte) 0, "Fire", "fire", "flame", "flames"),
    MOB_SPAWNER             (52,(byte) 0, "Mob spawner", "mobspawner", "spawner"),
    WOODEN_STAIRS           (53,(byte) 0, "Wooden stairs", "woodstair", "woodstairs", "woodenstair", "woodenstairs"),
    CHEST                   (54,(byte) 0, "Chest", "chest", "storage", "storagechest"),
    REDSTONE_WIRE           (55,(byte) 0, "Redstone wire", "redstone", "redstoneblock"),
    DIAMOND_ORE             (56,(byte) 0, "Diamond ore", "diamondore"),
    DIAMOND_BLOCK           (57,(byte) 0, "Diamond block", "diamond", "diamondblock"),
    WORKBENCH               (58,(byte) 0, "Workbench", "workbench", "table", "craftingtable", "crafting"),
    CROPS                   (59,(byte) 0, "Crops", "crops", "crop", "plant", "plants"),
    SOIL                    (60,(byte) 0, "Soil", "soil", "farmland"),
    FURNACE                 (61,(byte) 0, "Furnace", "furnace"),
    BURNING_FURNACE         (62,(byte) 0, "Furnace         (burning)", "burningfurnace", "litfurnace"),
    SIGN_POST               (63,(byte) 0, "Sign post", "sign", "signpost"),
    WOODEN_DOOR             (64,(byte) 0, "Wooden door", "wooddoor", "woodendoor", "door"),
    LADDER                  (65,(byte) 0, "Ladder", "ladder"),
    MINECART_TRACKS         (66,(byte) 0, "Minecart tracks", "track", "tracks", "minecrattrack", "minecarttracks", "rails", "rail"),
    COBBLESTONE_STAIRS      (67,(byte) 0, "Cobblestone stairs", "cobblestonestair", "cobblestonestairs", "cobblestair", "cobblestairs"),
    WALL_SIGN               (68,(byte) 0, "Wall sign", "wallsign"),
    LEVER                   (69,(byte) 0, "Lever", "lever", "switch", "stonelever", "stoneswitch"),
    STONE_PRESSURE_PLATE    (70,(byte) 0, "Stone pressure plate", "stonepressureplate", "stoneplate"),
    IRON_DOOR               (71,(byte) 0, "Iron Door", "irondoor"),
    WOODEN_PRESSURE_PLATE   (72,(byte) 0, "Wooden pressure plate", "woodpressureplate", "woodplate", "woodenpressureplate", "woodenplate", "plate", "pressureplate"),
    REDSTONE_ORE            (73,(byte) 0, "Redstone ore", "redstoneore"),
    GLOWING_REDSTONE_ORE    (74,(byte) 0, "Glowing redstone ore", "glowingredstoneore"),
    REDSTONE_TORCH_OFF      (75,(byte) 0, "Redstone torch         (off)", "redstonetorchoff", "rstorchoff"),
    REDSTONE_TORCH_ON       (76,(byte) 0, "Redstone torch         (on)", "redstonetorch", "redstonetorchon", "rstorchon", "redtorch"),
    STONE_BUTTON            (77,(byte) 0, "Stone Button", "stonebutton", "button"),
    SNOW                    (78,(byte) 0, "Snow", "snow"),
    ICE                     (79,(byte) 0, "Ice", "ice"),
    SNOW_BLOCK              (80,(byte) 0, "Snow block", "snowblock"),
    CACTUS                  (81,(byte) 0, "Cactus", "cactus", "cacti"),
    CLAY                    (82,(byte) 0, "Clay", "clay"),
    SUGAR_CANE              (83,(byte) 0, "Reed", "reed", "cane", "sugarcane", "sugarcanes", "vine", "vines"),
    JUKEBOX                 (84,(byte) 0, "Jukebox", "jukebox", "stereo", "recordplayer"),
    FENCE                   (85,(byte) 0, "Fence", "fence"),
    PUMPKIN                 (86,(byte) 0, "Pumpkin", "pumpkin"),
    NETHERRACK              (87,(byte) 0, "Netherrack", "redmossycobblestone", "redcobblestone", "redmosstone", "redcobble", "netherstone", "netherrack", "nether", "hellstone"),
    SOUL_SAND               (88,(byte) 0, "Soul sand", "slowmud", "mud", "soulsand", "hellmud"),
    GLOWSTONE               (89,(byte) 0, "Glowstone", "brittlegold", "glowstone", "lightstone", "brimstone", "australium"),
    PORTAL                  (90,(byte) 0, "Portal", "portal"),
    JACK_O_LANTERN          (91,(byte) 0, "Pumpkin (on)", "pumpkinlighted", "pumpkinon", "litpumpkin", "jackolantern"),
    CAKE                    (92,(byte) 0, "Cake", "cake", "cakeblock"),
    REDSTONE_REPEATER_OFF   (93,(byte) 0, "Redstone repeater (off)", "diodeoff", "redstonerepeater", "repeater", "delayer"),
    REDSTONE_REPEATER_ON    (94,(byte) 0, "Redstone repeater (on)", "diode", "diodeon", "redstonerepeateron", "repeateron", "delayeron"),
    LOCKED_CHEST            (95,(byte) 0, "Locked chest", "lockedchest", "steveco", "supplycrate", "valveneedstoworkonep3nottf2kthx"),
    TRAP_DOOR               (96,(byte) 0, "Trap door", "trapdoor", "hatch", "floordoor"),

    // Items
    IRON_SHOVEL             (256,(byte) 0, "Iron shovel", "ironshovel"),
    IRON_PICK               (257,(byte) 0, "Iron pick", "ironpick", "ironpickaxe"),
    IRON_AXE                (258,(byte) 0, "Iron axe", "ironaxe"),
    FLINT_AND_TINDER        (259,(byte) 0, "Flint and tinder", "flintandtinder", "lighter", "flintandsteel", "flintsteel", "flintandiron", "flintnsteel", "flintniron", "flintntinder"),
    RED_APPLE               (260,(byte) 0, "Red apple", "redapple", "apple"),
    BOW                     (261,(byte) 0, "Bow"),
    ARROW                   (262,(byte) 0, "Arrow"),
    COAL                    (263,(byte) 0, "Coal"),
    WOODCOAL                (263,(byte) 1, "Woodcoal"),
    DIAMOND                 (264,(byte) 0, "Diamond", "diamond"),
    IRON_BAR                (265,(byte) 0, "Iron bar", "ironbar", "iron"),
    GOLD_BAR                (266,(byte) 0, "Gold bar", "goldbar", "gold"),
    IRON_SWORD              (267,(byte) 0, "Iron sword", "ironsword"),
    WOOD_SWORD              (268,(byte) 0, "Wooden sword", "woodsword"),
    WOOD_SHOVEL             (269,(byte) 0, "Wooden shovel", "woodshovel"),
    WOOD_PICKAXE            (270,(byte) 0, "Wooden pickaxe", "woodpick", "woodpickaxe"),
    WOOD_AXE                (271,(byte) 0, "Wooden axe", "woodaxe"),
    STONE_SWORD             (272,(byte) 0, "Stone sword", "stonesword"),
    STONE_SHOVEL            (273,(byte) 0, "Stone shovel", "stoneshovel"),
    STONE_PICKAXE           (274,(byte) 0, "Stone pickaxe", "stonepick", "stonepickaxe"),
    STONE_AXE               (275,(byte) 0, "Stone pickaxe", "stoneaxe"),
    DIAMOND_SWORD           (276,(byte) 0, "Diamond sword", "diamondsword"),
    DIAMOND_SHOVEL          (277,(byte) 0, "Diamond shovel", "diamondshovel"),
    DIAMOND_PICKAXE         (278,(byte) 0, "Diamond pickaxe", "diamondpick", "diamondpickaxe"),
    DIAMOND_AXE             (279,(byte) 0, "Diamond axe", "diamondaxe"),
    STICK                   (280,(byte) 0, "Stick", "stick"),
    BOWL                    (281,(byte) 0, "Bowl", "bowl"),
    MUSHROOM_SOUP           (282,(byte) 0, "Mushroom soup", "mushroomsoup", "soup", "brbsoup"),
    GOLD_SWORD              (283,(byte) 0, "Golden sword", "goldsword"),
    GOLD_SHOVEL             (284,(byte) 0, "Golden shovel", "goldshovel"),
    GOLD_PICKAXE            (285,(byte) 0, "Golden pickaxe", "goldpick", "goldpickaxe"),
    GOLD_AXE                (286,(byte) 0, "Golden axe", "goldaxe"),
    STRING                  (287,(byte) 0, "String", "string"),
    FEATHER                 (288,(byte) 0, "Feather", "feather"),
    SULPHUR                 (289,(byte) 0, "Sulphur", "sulphur", "sulfur", "gunpowder"),
    WOOD_HOE                (290,(byte) 0, "Wooden hoe", "woodhoe"),
    STONE_HOE               (291,(byte) 0, "Stone hoe", "stonehoe"),
    IRON_HOE                (292,(byte) 0, "Iron hoe", "ironhoe"),
    DIAMOND_HOE             (293,(byte) 0, "Diamond hoe", "diamondhoe"),
    GOLD_HOE                (294,(byte) 0, "Golden hoe", "goldhoe"),
    SEEDS                   (295,(byte) 0, "Seeds", "seeds", "seed"),
    WHEAT                   (296,(byte) 0, "Wheat", "wheat"),
    BREAD                   (297,(byte) 0, "Bread", "bread"),
    LEATHER_HELMET          (298,(byte) 0, "Leather helmet", "leatherhelmet", "leatherhat"),
    LEATHER_CHEST           (299,(byte) 0, "Leather chestplate", "leatherchest", "leatherchestplate", "leathervest", "leatherbreastplate", "leatherplate", "leathercplate", "leatherbody"),
    LEATHER_PANTS           (300,(byte) 0, "Leather pants", "leatherpants", "leathergreaves", "leatherlegs", "leatherleggings", "leatherstockings", "leatherbreeches"),
    LEATHER_BOOTS           (301,(byte) 0, "Leather boots", "leatherboots", "leathershoes", "leatherfoot", "leatherfeet"),
    CHAINMAIL_HELMET        (302,(byte) 0, "Chainmail helmet", "chainmailhelmet", "chainmailhat"),
    CHAINMAIL_CHEST         (303,(byte) 0, "Chainmail chestplate", "chainmailchest", "chainmailchestplate", "chainmailvest", "chainmailbreastplate", "chainmailplate", "chainmailcplate", "chainmailbody"),
    CHAINMAIL_PANTS         (304,(byte) 0, "Chainmail pants", "chainmailpants", "chainmailgreaves", "chainmaillegs", "chainmailleggings", "chainmailstockings", "chainmailbreeches"),
    CHAINMAIL_BOOTS         (305,(byte) 0, "Chainmail boots", "chainmailboots", "chainmailshoes", "chainmailfoot", "chainmailfeet"),
    IRON_HELMET             (306,(byte) 0, "Iron helmet", "ironhelmet", "ironhat"),
    IRON_CHEST              (307,(byte) 0, "Iron chestplate", "ironchest", "ironchestplate", "ironvest", "ironbreastplate", "ironplate", "ironcplate", "ironbody"),
    IRON_PANTS              (308,(byte) 0, "Iron pants", "ironpants", "irongreaves", "ironlegs", "ironleggings", "ironstockings", "ironbreeches"),
    IRON_BOOTS              (309,(byte) 0, "Iron boots", "ironboots", "ironshoes", "ironfoot", "ironfeet"),
    DIAMOND_HELMET          (310,(byte) 0, "Diamond helmet", "diamondhelmet", "diamondhat"),
    DIAMOND_CHEST           (311,(byte) 0, "Diamond chestplate", "diamondchest", "diamondchestplate", "diamondvest", "diamondbreastplate", "diamondplate", "diamondcplate", "diamondbody"),
    DIAMOND_PANTS           (312,(byte) 0, "Diamond pants", "diamondpants", "diamondgreaves", "diamondlegs", "diamondleggings", "diamondstockings", "diamondbreeches"),
    DIAMOND_BOOTS           (313,(byte) 0, "Diamond boots", "diamondboots", "diamondshoes", "diamondfoot", "diamondfeet"),
    GOLD_HELMET             (314,(byte) 0, "Gold helmet", "goldhelmet", "goldhat"),
    GOLD_CHEST              (315,(byte) 0, "Gold chestplate", "goldchest", "goldchestplate", "goldvest", "goldbreastplate", "goldplate", "goldcplate", "goldbody"),
    GOLD_PANTS              (316,(byte) 0, "Gold pants", "goldpants", "goldgreaves", "goldlegs", "goldleggings", "goldstockings", "goldbreeches"),
    GOLD_BOOTS              (317,(byte) 0, "Gold boots", "goldboots", "goldshoes", "goldfoot", "goldfeet"),
    FLINT                   (318,(byte) 0, "Flint", "flint"),
    RAW_PORKCHOP            (319,(byte) 0, "Raw porkchop", "rawpork", "rawporkchop", "rawbacon", "baconstrips", "rawmeat"),
    COOKED_PORKCHOP         (320,(byte) 0, "Cooked porkchop", "pork", "cookedpork", "cookedporkchop", "cookedbacon", "bacon", "meat"),
    PAINTING                (321,(byte) 0, "Painting", "painting"),
    GOLD_APPLE              (322,(byte) 0, "Golden apple", "goldapple", "goldenapple"),
    SIGN                    (323,(byte) 0, "Wooden sign", "sign"),
    WOODEN_DOOR_ITEM        (324,(byte) 0, "Wooden door", "wooddoor", "door"),
    BUCKET                  (325,(byte) 0, "Bucket", "bucket", "bukkit"),
    WATER_BUCKET            (326,(byte) 0, "Water bucket", "waterbucket", "waterbukkit"),
    LAVA_BUCKET             (327,(byte) 0, "Lava bucket", "lavabucket", "lavabukkit"),
    MINECART                (328,(byte) 0, "Minecart", "minecart", "cart"),
    SADDLE                  (329,(byte) 0, "Saddle", "saddle"),
    IRON_DOOR_ITEM          (330,(byte) 0, "Iron door", "irondoor"),
    REDSTONE_DUST           (331,(byte) 0, "Redstone dust", "redstonedust", "reddust", "redstone", "dust", "wire"),
    SNOWBALL                (332,(byte) 0, "Snowball", "snowball"),
    WOOD_BOAT               (333,(byte) 0, "Wooden boat", "woodboat", "woodenboat", "boat"),
    LEATHER                 (334,(byte) 0, "Leather", "leather", "cowhide"),
    MILK_BUCKET             (335,(byte) 0, "Milk bucket", "milkbucket", "milk", "milkbukkit"),
    BRICK_BAR               (336,(byte) 0, "Brick", "brickbar"),
    CLAY_BALL               (337,(byte) 0, "Clay", "clay"),
    SUGAR_CANE_ITEM         (338,(byte) 0, "Sugar cane", "sugarcane", "reed", "reeds"),
    PAPER                   (339,(byte) 0, "Paper", "paper"),
    BOOK                    (340,(byte) 0, "Book", "book"),
    SLIME_BALL              (341,(byte) 0, "Slime ball", "slimeball", "slime"),
    STORAGE_MINECART        (342,(byte) 0, "Storage minecart", "storageminecart", "storagecart"),
    POWERED_MINECART        (343,(byte) 0, "Powered minecart", "poweredminecart", "poweredcart"),
    EGG                     (344,(byte) 0, "Egg", "egg"),
    COMPASS                 (345,(byte) 0, "Compass", "compass"),
    FISHING_ROD             (346,(byte) 0, "Fishing rod", "fishingrod", "fishingpole"),
    WATCH                   (347,(byte) 0, "Watch", "watch", "clock", "timer"),
    LIGHTSTONE_DUST         (348,(byte) 0, "Glowstone dust", "lightstonedust", "glowstonedone", "brightstonedust", "brittlegolddust", "brimstonedust"),
    RAW_FISH                (349,(byte) 0, "Raw fish", "rawfish", "fish"),
    COOKED_FISH             (350,(byte) 0, "Cooked fish", "cookedfish"),
    DYE_BLACK               (351,(byte) 0, "Ink sac", "inksac", "ink", "dye", "inksack"),
    DYE_RED                 (351,(byte) 1, "Rose Red", "Red dre"),
    DYE_DARKGREEN           (351,(byte) 2, "Lapis Lazuli Blue", "inksac"),    
    DYE_DARKBLUE            (351,(byte) 3, "Cactus Green", "inksac"),
    DYE_BROWN               (351,(byte) 4, "Cocoa Beans Brown", "inksac"),
    DYE_PURPLE              (351,(byte) 5, "Purple Dye", "inksac"),
    DYE_CYAN                (351,(byte) 6, "Cyan Dye", "inksac"),
    DYE_LIGHTGRAY           (351,(byte) 7, "Light Gray Dye", "inksac"),
    DYE_GRAY                (351,(byte) 8, "Gray Dye", "inksac"),
    DYE_PINK                (351,(byte) 9, "Pink Dye", "inksac"),
    DYE_LIME                (351,(byte) 10, "Lime Dye", "inksac"),
    DYE_YELLOW              (351,(byte) 11, "Dandelion Yellow", "inksac"),
    DYE_LIGHTBLUE           (351,(byte) 12, "Light Blue Dye", "inksac"),
    DYE_MAGENTA             (351,(byte) 13, "Magenta Dye", "inksac"),
    DYE_ORANGE              (351,(byte) 14, "Orange Dye", "inksac"),
    DYE_WHITE               (351,(byte) 15, "Bone Meal", "inksac"),
    BONE                    (352,(byte) 0, "Bone", "bone"),
    SUGAR                   (353,(byte) 0, "Sugar", "sugar"),
    CAKE_ITEM               (354,(byte) 0, "Cake", "cake"),
    BED_ITEM                (355,(byte) 0, "Bed", "bed"),
    REDSTONE_REPEATER       (356,(byte) 0, "Redstone repeater", "redstonerepeater", "diode", "delayer", "repeater"),
    COOKIE                  (357,(byte) 0, "Cookie", "cookie"),
    MAP                     (358,(byte) 0, "Map", "map"),
    GOLD_RECORD             (2256,(byte) 0, "Gold Record", "goldrecord", "golddisc"),
    GREEN_RECORD            (2257,(byte) 0, "Green Record", "greenrecord", "greenddisc");
    
    
    private int id;
    private byte data;
    private String []names;
    
    
    Items(int id,byte databyte, String ... name){
        this.id=id;
        this.data=databyte;
        this.names=name;
    }
    
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.names[0];
    }
    public String [] getNames(){
        return this.names;
    }
    public byte getByte(){
        return this.data;
    }
    
    private static final Map<Integer,Items> ids = new HashMap<Integer,Items>();
    private static final Map<String,Items> lookup = new LinkedHashMap<String,Items>();

    static {
        for (Items type : EnumSet.allOf(Items.class)) {
            ids.put(type.id, type);
            for (String key : type.names) {
                lookup.put(key.toUpperCase(), type);
            }
        }
    }
    
    public static boolean iswool(Items item){
        return item.id == 35;
    }
    public boolean iswool(){
        return Items.iswool(this);
    }
    
    public static Items getItem(int id){
        return Items.ids.get(id);
    }
    public static Items getItem(String name){
        try{
            int id=Integer.parseInt(name);
            return getItem(id);
        }catch(NumberFormatException e){
            return Items.lookup.get(name.toUpperCase());
        } 
    }
    public static Items getItem(int id,byte data){
        for (Items type : EnumSet.allOf(Items.class)) {
            if(type.id==id&&type.data==data)
                return type;
        }
        return null;
    }
   
    public static boolean islequid(Items item){
        return  item.id == 8 || 
                item.id == 9 || 
                item.id == 10 || 
                item.id == 11;   
    }
    public boolean islequid(){
        return Items.islequid(this);
    }
    
    public static boolean ispervious (Items item){
        return  item.id == 0 ||
                item.id == 6 ||
                item.id == 8 ||
                item.id == 9 ||
                item.id == 10 ||
                item.id == 11 ||
                item.id == 27 ||
                item.id == 28 ||
                item.id == 30 ||
                item.id == 31 || 
                item.id == 32 ||
                item.id == 37 ||
                item.id == 38 ||
                item.id == 39 ||
                item.id == 40 ||
                item.id == 37 ||
                item.id == 50 ||
                item.id == 51 ||
                item.id == 55 ||
                item.id == 59 ||
                item.id == 60 ||
                item.id == 63 ||
                item.id == 64 ||
                item.id == 65 ||
                item.id == 66 ||
                item.id == 68 ||
                item.id == 70 ||
                item.id == 71 ||
                item.id == 72 ||
                item.id == 75 ||
                item.id == 76 ||
                item.id == 77 ||
                item.id == 78 ||
                item.id == 83 ||
                item.id == 90 ||
                item.id == 93 ||
                item.id == 94 ||
                item.id == 96;
    }
    public boolean ispervious(){
        return Items.ispervious(this);
    }
     
    public static boolean ishard(Items item){
        return !Items.ispervious(item);
    }
    public boolean ishard(){
        return Items.ishard(this);
    }

    public static boolean iseatable(Items item){
        return  item.id == 260 ||
                item.id == 297 ||
                item.id == 320 ||
                item.id == 322 ||
                item.id == 350 ||
                item.id == 354 ||
                item.id == 357;       
    }
    public boolean iseatable(){
        return Items.iseatable(this);
    }
    
    public static boolean isBlock(Items item){
        return item.id < 256;
    }
    public boolean isBlock(){
        return Items.isBlock(this);
    }
    
    public static boolean isItem(Items item){
        return item.id >= 256;
    }
    public boolean isItem(){
        return Items.isItem(this);
    }
    
    public static boolean isOre(Items item){
        return  item.id == 14 ||
                item.id == 15 ||
                item.id == 16 ||
                item.id == 21 ||
                item.id == 56 ||
                item.id == 73 ||
                item.id == 74;        
    }
    public boolean isOre(){
        return Items.isOre(this);
    }
    
    public static boolean isVhicle(Items item){
        return  item.id == 328 ||
                item.id == 333 ||
                item.id == 342 ||
                item.id == 343;
    }
    public boolean isVhicle(){
        return Items.isVhicle(this);
    }
    
    public static int getId(Items item){
        return item.id;
    }
    public static byte getByte(Items item){
        return item.data;
    }
    public String getName(Items item){
        return item.names[0];
    }
    public String [] getNames(Items item){
        return item.names;
    }
    

}
