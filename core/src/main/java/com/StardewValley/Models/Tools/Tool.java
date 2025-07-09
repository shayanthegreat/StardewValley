package com.StardewValley.Models.Tools;

import com.StardewValley.Models.Animal.*;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Crafting.Material;
import com.StardewValley.Models.Crafting.MaterialType;
import com.StardewValley.Models.Enums.Season;
import com.StardewValley.Models.Enums.SkillType;
import com.StardewValley.Models.Enums.Weather;
import com.StardewValley.Models.Farming.Crop;
import com.StardewValley.Models.Farming.ForagingCrop;
import com.StardewValley.Models.Farming.Plant;
import com.StardewValley.Models.Farming.Seed;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Interactions.Messages.GameMessage;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.*;
import com.StardewValley.Models.Time;

import java.io.Serializable;

import static com.StardewValley.Models.Enums.SkillType.foraging;
import static com.StardewValley.Models.Enums.SkillType.mining;

public class Tool extends Item implements Serializable {
    private final ToolType toolType;
    protected int level = 0;

    public Tool(ToolType toolType) {
        super(toolType.getName(), 0, false);
        this.toolType = toolType;
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public LevelInfo getLevelInfo() {
//        TODO: get level info from tool type
        return toolType.getLevel(level);
    }

    public void upgradeLevel() {
        if (toolType.getLevels().size() > level + 1) {
            level++;
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getPrice() {
        return 0;
//        TODO: fix this
    }

    @Override
    public void use() {

    }

    public GameMessage use(Position position) {
        Game game = App.getInstance().getCurrentGame();
        Map map = game.getMap();
        Tile tile = map.getTile(position);
        BackPack backPack = game.getCurrentPlayer().getBackPack();

        double mp = 1;
        if (game.getTodayWeather().equals(Weather.rain) || game.getTodayWeather().equals(Weather.storm)) {
            mp *= 1.5;
        }
        if (game.getTodayWeather().equals(Weather.snow)) {
            mp *= 2;
        }
        switch (toolType) {
            case hoe -> {
                boolean isSuccess;
                if (tile.isBuildingPlantable() && tile.getObject() == null) {
                    isSuccess = true;
                } else {
                    isSuccess = false;
                }
                int energy = (int) (toolType.getEnergy(level, isSuccess) * mp);
                if (energy > game.getCurrentPlayer().getEnergy().getAmount()) {
                    return new GameMessage(null, "You don't have enough energy to use this tool.");
                } else if (!isSuccess) {
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    return new GameMessage(null, "You couldn't use this tool.");
                } else {
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    tile.setPlowed(true);
                    return new GameMessage(null, "You plowed this tile");
                }
            }
            case axe -> {
                boolean isSuccess;
                if (tile.isBuildingPlantable() && tile.getObject() != null && tile.getObject() instanceof Plant) {
                    isSuccess = true;
                } else {
                    isSuccess = false;
                }
                int energy = (int) (toolType.getEnergy(level, isSuccess) * mp);
                if (energy > game.getCurrentPlayer().getEnergy().getAmount()) {
                    return new GameMessage(null, "You don't have enough energy to use this tool.");
                } else if (!isSuccess) {
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    return new GameMessage(null, "You couldn't use this tool.");
                } else {
                    Plant plant = (Plant) tile.getObject();
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    tile.setObject(null);
                    if (plant.getType().isTree()) {
                        tile.setObject(new Seed(plant.getType().getSeed()));
                    }
                    game.getCurrentPlayer().getBackPack().addItem(new Material(MaterialType.wood), 50);
                    return new GameMessage(null, "You chopped this tile");
                }
            }
            case wateringCan -> {
                int options = 0;
                if (tile.getBuilding() != null && tile.getBuilding() instanceof Lake) {
                    options = 1;
                } else if (tile.isBuildingPlantable() && tile.getObject() != null && tile.getObject() instanceof Plant) {
                    options = 2;
                }
                int energy = (int) (toolType.getEnergy(level, options != 0) * mp);
                if (energy > game.getCurrentPlayer().getEnergy().getAmount()) {
                    return new GameMessage(null, "You don't have enough energy to use this tool.");
                } else if (options == 0) {
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    return new GameMessage(null, "You couldn't use this tool.");
                } else {
                    if (options == 2) {
                        game.getCurrentPlayer().decreaseEnergy(energy);
                        Plant plant = (Plant) tile.getObject();
                        WateringCan wateringCan = (WateringCan) game.getCurrentPlayer().getBackPack().getToolByType(ToolType.wateringCan);
                        if (wateringCan.getCurrentCapacity() == 0) {
                            return new GameMessage(null, "There is no water in your watering can!");
                        } else {
                            if (plant.getGianPosition() != -1) {
                                for (Plant p : plant.getGianPlants()) {
                                    p.setLastWateringTime(new Time(game.getTime()));
                                }
                            } else {
                                plant.setLastWateringTime(new Time(game.getTime()));
                            }
                            wateringCan.decreaseCapacity(1);
                            return new GameMessage(null, "You successfully watered this plant!");
                        }
                    } else {
                        game.getCurrentPlayer().decreaseEnergy(energy);
                        WateringCan wateringCan = (WateringCan) game.getCurrentPlayer().getBackPack().getToolByType(ToolType.wateringCan);
                        wateringCan.fill();
                        return new GameMessage(null, "You filled your WateringCan");
                    }
                }
            }
            case scythe -> {
                boolean isSuccess = false;
                if (tile.isBuildingPlantable()) {
                    if (tile.getObject() != null && tile.getObject() instanceof Plant) {
                        isSuccess = true;
                    }
                }

                int energy = (int) (toolType.getEnergy(level, isSuccess) * mp);
                if (energy > game.getCurrentPlayer().getEnergy().getAmount()) {
                    return new GameMessage(null, "You don't have enough energy to use this tool.");
                } else if (!isSuccess) {
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    return new GameMessage(null, "You couldn't use this tool.");
                } else {
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    Plant plant = (Plant) tile.getObject();
                    int daysDiff = Math.abs(Time.getDayDifference(plant.getPlantingTime(), game.getTime()));
                    if (plant.getType().getTotalTime() > daysDiff) {
                        //System.out.println(plant.getType().getTotalTime() + " " + daysDiff);
                        return new GameMessage(null, "Let your tree grow as it should");
                    }
                    int amount;
                    if (plant.getGianPosition() == -1) {
                        amount = 1;
                        plant.addRegrownTimes();
                    } else {
                        amount = Math.min(10, backPack.getMaxCapacity() - backPack.getCapacity());
                        amount = Math.max(amount, 0);
                        for (Plant giantPlants : plant.getGianPlants()) {
                            giantPlants.addRegrownTimes();
                        }
                    }
                    if(plant.getRegrownTimes() >= plant.getType().getReGrowth()) {
                        plant.destroy();
                    }
                    if (!backPack.canAddItem(new Crop(plant.getType().getCrop()), amount)) {
                        return new GameMessage(null, "You don't have enough capacity in backpack");
                    }
                    backPack.addItem(new Crop(plant.getType().getCrop()), amount);
                    return new GameMessage(null, "You got some fruit! (" + amount + "x)");
                }
            }
            case shear -> {
                boolean isSuccess = false;
                int cnt = 0;
                if (tile.getBuilding() == null && tile.getObject() != null && tile.getObject() instanceof Barn) {
                    for (Animal animal : ((Barn) tile.getObject()).getAnimals()) {
                        if (animal.getType() == AnimalType.rabbit || animal.getType() == AnimalType.sheep) {
                            isSuccess = true;
                        }
                    }
                }
                int energy = (int) (toolType.getEnergy(level, isSuccess) * mp);
                if (energy > game.getCurrentPlayer().getEnergy().getAmount()) {
                    return new GameMessage(null, "You don't have enough energy to use this tool.");
                } else if (!isSuccess) {
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    return new GameMessage(null, "You couldn't use this tool.");
                } else {
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    for (Animal animal : ((Barn) tile.getObject()).getAnimals()) {
                        if (animal.getType() == AnimalType.rabbit) {
                            backPack.addItem(new AnimalProduct(AnimalProductType.rabbitWool), 1);
                        } else if (animal.getType() == AnimalType.sheep) {
                            backPack.addItem(new AnimalProduct(AnimalProductType.wool), 1);
                        }
                    }
                    return new GameMessage(null, "You collected some wools");
                }
            }
            case milkPail -> {
                boolean isSuccess = false;
                if (tile.getBuilding() == null && tile.getObject() != null && tile.getObject() instanceof Barn) {
                    for (Animal animal : ((Barn) tile.getObject()).getAnimals()) {
                        if (animal.getType() == AnimalType.cow || animal.getType() == AnimalType.goat) {
                            isSuccess = true;
                        }
                    }
                }
                int energy = (int) (toolType.getEnergy(level, isSuccess) * mp);
                if (energy > game.getCurrentPlayer().getEnergy().getAmount()) {
                    return new GameMessage(null, "You don't have enough energy to use this tool.");
                } else if (!isSuccess) {
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    return new GameMessage(null, "You couldn't use this tool.");
                } else {
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    for (Animal animal : ((Barn) tile.getObject()).getAnimals()) {
                        if (animal.getType() == AnimalType.goat) {
                            backPack.addItem(new AnimalProduct(AnimalProductType.goatMilk), 1);
                        } else if (animal.getType() == AnimalType.cow) {
                            backPack.addItem(new AnimalProduct(AnimalProductType.milk), 1);
                        }
                    }
                    return new GameMessage(null, "You collected some milk :)");
                }
            }
            case pickaxe -> {
                boolean isSuccess;
                if (tile.getObject() != null && tile.getObject() instanceof Item) {
                    isSuccess = true;
                } else {
                    isSuccess = false;
                }
                int energy = (int) (toolType.getEnergy(level, isSuccess) * mp);
                if (energy > game.getCurrentPlayer().getEnergy().getAmount()) {
                    return new GameMessage(null, "You don't have enough energy to use this tool.");
                } else if (!isSuccess) {
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    return new GameMessage(null, "You couldn't use this tool.");
                } else {
                    game.getCurrentPlayer().decreaseEnergy(energy);
                    Item item = (Item) tile.getObject();
                    backPack.addItem(item, 1);
                    if (item instanceof Material) {
                        if (((Material) item).getType() == MaterialType.stone) {
                            App.getInstance().getCurrentGame().getCurrentPlayer().getSkill(mining).addAmount(10);
                            if (App.getInstance().getCurrentGame().getCurrentPlayer().getSkill(mining).getLevel() == 2) {
                                backPack.addItem(item, 10);
                            }
                        }
                        return new GameMessage(null, "You collected some stone!");
                    }
                    if (item instanceof ForagingCrop || item instanceof Seed) {
                        App.getInstance().getCurrentGame().getCurrentPlayer().getSkill(foraging).addAmount(10);
                    }
                    return new GameMessage(null, "You collected some " + item.getName() + " !");
                }
            }
        }
        return new GameMessage(null, "nothing happened!?");
    }

    @Override
    public void drop(Tile tile) {
        tile.setObject(Tool.this);
    }

    @Override
    public void delete() {

    }

    @Override
    public String getName() {
        return toolType.getName();
    }

    public ToolType getToolType() {
        return toolType;
    }

    @Override
    public String toString() {
        return getLevelInfo().levelName() + " " + getName();
    }
}
