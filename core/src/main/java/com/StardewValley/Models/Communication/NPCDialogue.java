package com.StardewValley.Models.Communication;

import com.StardewValley.Models.Enums.Season;
import com.StardewValley.Models.Enums.Weather;
import com.StardewValley.Models.Time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class NPCDialogue implements Serializable {
    private ArrayList<String> Dialogues;
    public NPCDialogue(NPC npc) {
        switch (npc.getName()) {
            case "Sebastian": {
                Dialogues = new ArrayList<>();
                Dialogues.add("What a perfect spring morning. I'm glad I get to share it with a friend like you.");
                Dialogues.add("Spring mornings are nice. Glad you're around to enjoy it.");
                Dialogues.add("Nice morning, huh? Don't get in the way of my work.");
                Dialogues.add("Summer rain always feels refreshing. Want to walk together?");
                Dialogues.add("Rainy afternoons like this make me think. You too?");
                Dialogues.add("Tch. Rain again. Just my luck.");
                Dialogues.add("Stormy fall evenings make me want to stay in and talk with someone close.");
                Dialogues.add("Storm's loud, huh? I don’t mind you hanging around.");
                Dialogues.add("This storm's a mess. Why are you still out?");
                Dialogues.add("Snowy nights like this are beautiful. I’m glad you're here.");
                Dialogues.add("It's cold out. Stay close, okay?");
                Dialogues.add("You out here too? Don’t catch a cold.");
                Dialogues.add("Nice to see you again. These days are better with you around.");
                Dialogues.add("Hey, you're becoming a familiar face.");
                Dialogues.add("Do I know you? Oh... right. You're that new person.");
                break;
            }
            case "Abigail": {
                Dialogues = new ArrayList<>();
                Dialogues.add("Spring is so colorful. It almost makes me want to paint... almost.");
                Dialogues.add("I love wandering in the woods this time of year. Want to come?");
                Dialogues.add("Sun’s out, sword’s out. Time for a little adventure!");
                Dialogues.add("This rain feels like a sad song... but a good one.");
                Dialogues.add("Rainy days make the world feel like a video game cutscene.");
                Dialogues.add("Ugh, rain again. At least it fits the gloomy vibe I like.");
                Dialogues.add("Fall is perfect for spooky stories and long walks through crunchy leaves.");
                Dialogues.add("Stormy nights are great for ghost hunting. Or just hanging out.");
                Dialogues.add("A storm? Perfect. Let’s pretend we’re trapped in a haunted castle!");
                Dialogues.add("Snow always reminds me of fairytales. Want to build a snow fort?");
                Dialogues.add("Cold fingers make it hard to play games, but I’ll manage.");
                Dialogues.add("I didn’t expect to see you out in the snow. Brave soul.");
                Dialogues.add("You keep showing up. I like that about you.");
                Dialogues.add("Hey! I was just thinking about you.");
                Dialogues.add("So you're the new person in town. Hope you're ready for weirdness.");
                break;
            }
            case "Harvey": {
                Dialogues = new ArrayList<>();
                Dialogues.add("Ah, the spring air is good for the lungs. Make sure to take deep breaths.");
                Dialogues.add("Don’t forget to get your daily sunlight. It's important for your health.");
                Dialogues.add("Lovely morning, isn’t it? Good weather makes for fewer colds.");
                Dialogues.add("Rainy days like these are perfect for catching up on reading.");
                Dialogues.add("Rain can feel gloomy, but I find it peaceful in moderation.");
                Dialogues.add("Be careful not to slip in this wet weather. Safety first!");
                Dialogues.add("Cool autumn evenings are great for long walks. Clears the mind.");
                Dialogues.add("Stormy weather? Please stay indoors if it gets worse.");
                Dialogues.add("I worry about people being out during storms. Are you alright?");
                Dialogues.add("The snow is beautiful, but it can be dangerous. Keep warm.");
                Dialogues.add("Cold temperatures like these are tough on the joints. Bundle up.");
                Dialogues.add("If you're feeling under the weather, stop by the clinic.");
                Dialogues.add("You're looking well. Keep it up!");
                Dialogues.add("Nice to see you again. How have you been?");
                Dialogues.add("Ah yes, you're the new resident. Welcome.");
                break;
            }
            case "Lia": {
                Dialogues = new ArrayList<>();
                Dialogues.add("Spring breathes life into everything. I get so inspired to sculpt.");
                Dialogues.add("Nature is waking up. Let’s go admire it together.");
                Dialogues.add("The flowers in bloom make me want to create something new.");
                Dialogues.add("Rain gives the forest a deeper color. It’s beautiful.");
                Dialogues.add("I love the sound of raindrops on my cabin’s roof.");
                Dialogues.add("It’s raining. A perfect excuse to cozy up with a warm drink.");
                Dialogues.add("Fall colors are a painter’s dream. Have you noticed the trees?");
                Dialogues.add("Storms can be chaotic, but there's beauty in the wild energy.");
                Dialogues.add("I stay in and work when storms hit. Too messy out there.");
                Dialogues.add("Snow turns everything into a quiet painting. I love it.");
                Dialogues.add("Winter nights are for reflecting and creating. Or just relaxing.");
                Dialogues.add("You came through the snow to see me? That’s sweet.");
                Dialogues.add("You're becoming part of my daily life. I like that.");
                Dialogues.add("I’m glad we keep running into each other.");
                Dialogues.add("You must be the new farmer everyone’s been talking about.");
                break;
            }
            case "Robin": {
                Dialogues = new ArrayList<>();
                Dialogues.add("Spring's great for building. Ground’s soft and days are long.");
                Dialogues.add("If you need any upgrades this season, let me know early!");
                Dialogues.add("Beautiful day. I’m thinking of adding a new porch to our house.");
                Dialogues.add("Rainy days are good for indoor projects.");
                Dialogues.add("Nothing like a warm fire and the sound of rain outside.");
                Dialogues.add("Rain makes outdoor work harder, but I don’t mind a challenge.");
                Dialogues.add("Fall’s when I stock up on wood. The air smells so fresh.");
                Dialogues.add("Storm’s loud tonight. Hope nothing breaks... again.");
                Dialogues.add("I’ve got a few storm repairs lined up. Busy season!");
                Dialogues.add("Snow means it’s time to work inside. I’ve got big plans.");
                Dialogues.add("Don’t let the cold stop you from visiting. I’m always working on something fun.");
                Dialogues.add("You’re braving the snow too? Must be something important.");
                Dialogues.add("Nice to see you again. Need help with anything?");
                Dialogues.add("You’re really making a name for yourself around here.");
                Dialogues.add("Hey, you’re that new farmer! Welcome to the valley.");
                break;
            }
        }
    }
    public String GetDialogue(Weather weather, Time time, int friendShipLevel) {
        int hour = time.getHour();
        Season season = time.getSeason();

        if (hour >= 5 && hour < 12 && season == Season.spring && weather == Weather.sunny) {
            if (friendShipLevel == 3) return Dialogues.get(0);
            if (friendShipLevel == 2) return Dialogues.get(1);
            return Dialogues.get(2);
        }

        if (hour >= 12 && hour < 17 && season == Season.summer && weather == Weather.rain) {
            if (friendShipLevel == 3) return Dialogues.get(3);
            if (friendShipLevel == 2) return Dialogues.get(4);
            return Dialogues.get(5);
        }

        if (hour >= 17 && hour < 21 && season == Season.fall && weather == Weather.storm) {
            if (friendShipLevel == 3) return Dialogues.get(6);
            if (friendShipLevel == 2) return Dialogues.get(7);
            return Dialogues.get(8);
        }
        if ((hour >= 21 || hour < 5) && season == Season.winter && weather == Weather.snow) {
            if (friendShipLevel == 3) return Dialogues.get(9);
            if (friendShipLevel == 2) return Dialogues.get(10);
            return Dialogues.get(11);
        }

        if (friendShipLevel == 3) return Dialogues.get(12);
        if (friendShipLevel == 2) return Dialogues.get(13);
        return Dialogues.get(14);
    }

}
