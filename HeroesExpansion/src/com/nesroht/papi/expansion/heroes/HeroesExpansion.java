package com.nesroht.papi.expansion.heroes;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.skill.Skill;
import com.herocraftonline.heroes.util.Properties;

/**
* This class will automatically register as a placeholder expansion
* when a jar including this class is added to the /plugins/placeholderapi/expansions/ folder
*
*/
public class HeroesExpansion extends PlaceholderExpansion {

    private Heroes heroes;
    /**
     * Since this expansion requires api access to the plugin "SomePlugin"
     * we must check if "SomePlugin" is on the server in this method
     */

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin(this.getRequiredPlugin()) != null;
    }

    /**
     * We can optionally override this method if we need to initialize variables within this class if we need to
     * or even if we have to do other checks to ensure the hook is properly setup.
     */
    @Override
    public boolean register() {
        if(!canRegister()){
            return false;
        }
        this.heroes = (Heroes)Bukkit.getPluginManager().getPlugin(this.getRequiredPlugin());

        if(this.heroes == null){
            return false;
        }

        return super.register();
    }

    /**
     * The name of the person who created this expansion should go here
     */
    @Override
    public String getAuthor() {
        return "Senroht";
    }

    /**
     * The placeholder identifier should go here
     * This is what tells PlaceholderAPI to call our onPlaceholderRequest method to obtain
     * a value if a placeholder starts with our identifier.
     * This must be unique and can not contain % or _
     */
    @Override
    public String getIdentifier() {
        return "heroes";
    }

    /**
     * if an expansion requires another plugin as a dependency, the proper name of the dependency should
     * go here. Set this to null if your placeholders do not require another plugin be installed on the server
     * for them to work. This is extremely important to set if you do have a dependency because
     * if your dependency is not loaded when this hook is registered, it will be added to a cache to be
     * registered when plugin: "getPlugin()" is enabled on the server.
     */
    /**@Override
    public String getPlugin() {
        return "Heroes";
    }**/

    /**
     * Returns the name of the required plugin.
     *
     * @return {@code DeluxeTags} as String
     */
    @Override
    public String getRequiredPlugin() {
        return "Heroes";
    }
    /**
     * This is the version of this expansion
     */
    @Override
    public String getVersion() {
        return "1.0.1";
    }


    /**
     * This is the method called when a placeholder with our identifier is found and needs a value
     * We specify the value identifier in this method
     */
    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        if (p == null) {
            return "";
        }
        String sk;

        Hero h = heroes.getCharacterManager().getHero(p);
        // %example_placeholder1%
        if (identifier.equals("party_leader")) {
        	try {
        		if (h.getParty().getLeader() != null) {
            		return String.valueOf(h.getParty().getLeader().getName());
            	}
        	}
        	catch (NullPointerException e) {
        		return String.valueOf(h.getName());
        	}
        	
        }
        else if (identifier.equals("party_size")) {
        	try {
        		if (h.getParty().getMembers().size() > 1) {
            		return String.valueOf(h.getParty().getMembers().size());
            	}
        		else {
        			return "1";
        		}
        	}
        	catch (NullPointerException e) {
        		return String.valueOf(1);
        	}
        }
        else if (identifier.equals("stamina")) {
        	return String.valueOf(h.getStamina());
        }
        else if (identifier.equals("maxstamina")) {
        	return String.valueOf(h.getMaxStamina());
        }
        else if (identifier.equals("mana")) {
        	return String.valueOf(h.getMana());
        }
        else if (identifier.equals("maxmana")) {
        	return String.valueOf(h.getMaxMana());
        }
        else if (identifier.equals("class")) {
        	return String.valueOf(h.getHeroClass().getName());
        }
        else if (identifier.equals("class2")) {
        	return String.valueOf(h.getSecondaryClass().getName());
        }
        else if (identifier.equals("class_level")) {
        	return String.valueOf(h.getHeroLevel(h.getHeroClass()));
        }
        else if (identifier.equals("class2_level")) {
        	return String.valueOf(h.getTieredLevel(h.getSecondaryClass()));
        }
        else if (identifier.equals("class_exp")) {
        	if (h.getHeroLevel(h.getHeroClass()) == 1) {
        		return String.valueOf(Math.round(h.getExperience(h.getHeroClass())));
        	}
        	else {
        		return String.valueOf(Math.round(h.getExperience(h.getHeroClass())-Properties.getTotalExp(h.getHeroLevel(h.getHeroClass()))));
        	}
        }
        else if (identifier.equals("class2_exp")) {
        	if (h.getHeroLevel(h.getSecondaryClass()) == 1) {
        		return String.valueOf(Math.round(h.getExperience(h.getSecondaryClass())));
        	}
        	else {
        		return String.valueOf(Math.round(h.getExperience(h.getSecondaryClass())-Properties.getTotalExp(h.getHeroLevel(h.getSecondaryClass()))));
        	}
        }
        else if (identifier.equals("class_maxexp")) {
        	if (h.getHeroLevel(h.getHeroClass()) != h.getHeroClass().getMaxLevel()) {
        		return String.valueOf(Math.round(Properties.getExp(h.getHeroLevel(h.getHeroClass())+1)));
        	}
        	return "0";
        }
        else if (identifier.equals("class2_maxexp")) {
        	if (h.getHeroLevel(h.getSecondaryClass()) != h.getSecondaryClass().getMaxLevel()) {
        		return String.valueOf(Math.round(Properties.getExp(h.getHeroLevel(h.getSecondaryClass())+1)));
        	}
        	return "0";
        }
        else if (identifier.equals("class_exp_percent")) {
        	if (h.getHeroLevel(h.getHeroClass()) != h.getHeroClass().getMaxLevel()) {
        		return String.valueOf(Math.round(h.getExpPercent(h.getHeroClass())));
        	}
        	return "100";
        }
        else if (identifier.equals("class2_exp_percent")) {
        	if (h.getHeroLevel(h.getSecondaryClass()) != h.getSecondaryClass().getMaxLevel()) {
            	return String.valueOf(Math.round(h.getExpPercent(h.getSecondaryClass())));
        	}
        	return "100";
        }
        else if (identifier.startsWith("skill_level_")){
          sk = identifier.split("skill_level_")[1];
          
          if ((sk == null) || (sk.isEmpty())) {
            return "";
          }
          
          Skill skill = heroes.getSkillManager().getSkill(sk);
          
          if (skill == null) {
            return "INVALID SKILL";
          }
          
          return String.valueOf(h.getHeroSkillLevel(skill));
        }
        else if (identifier.equals("class_description")) {
        	return String.valueOf(h.getHeroClass().getDescription());
        }
        else if (identifier.equals("class2_description")) {
        	return String.valueOf(h.getSecondaryClass().getDescription());
        }
        else if (identifier.equals("class_tier")) {
        	return String.valueOf(h.getHeroClass().getTier());
        }
        else if (identifier.equals("class2_tier")) {
        	return String.valueOf(h.getSecondaryClass().getTier());
        }
        else if (identifier.equals("mastered")) {
        	if (h.getMasteredClasses().size() > 1) {
        		return String.valueOf(h.getMasteredClasses());
        	}
        	else {
        		return "None";
        	}
        }
        else if (identifier.startsWith("ismastered_")){
            sk = identifier.split("ismastered_")[1];
            
            if ((sk == null) || (sk.isEmpty())) {
              return "";
            }
            boolean is = false;
            for (String classname : h.getMasteredClasses()) {
            	if (sk.equalsIgnoreCase(classname)) {
            		is = true;
            	}
            }
            
            if (is == false) {
            	return "&c";
            }
            
            return "&6";
        }
        else if (identifier.equals("class_ismastered")) {
        	sk = h.getHeroClass().getName();
        	boolean is = false;
            for (String classname : h.getMasteredClasses()) {
            	if (sk.equalsIgnoreCase(classname)) {
            		is = true;
            	}
            }
            
            if (is == false) {
            	return "&c";
            }
            
            return "&6";
        }
        else if (identifier.equals("class2_ismastered")) {
        	sk = h.getSecondaryClass().getName();
        	boolean is = false;
            for (String classname : h.getMasteredClasses()) {
            	if (sk.equalsIgnoreCase(classname)) {
            		is = true;
            	}
            }
            
            if (is == false) {
            	return "&c";
            }
            
            return "&6";
        }
        else if (identifier.equals("class_maxlevel")) {
        	return String.valueOf(h.getHeroClass().getMaxLevel());
        }
        else if (identifier.equals("class2_maxlevel")) {
        	return String.valueOf(h.getSecondaryClass().getMaxLevel());
        }
        
        return null;
    }
}