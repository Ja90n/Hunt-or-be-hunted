package com.ja90n.huntorbehunted.runnables;

import com.ja90n.huntorbehunted.HuntOrBeHunted;
import com.ja90n.huntorbehunted.instances.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PowerUpSpawnRunnable extends BukkitRunnable {

    private HuntOrBeHunted huntOrBeHunted;
    private ArrayList<Location> spawnLocations;

    public PowerUpSpawnRunnable(HuntOrBeHunted huntOrBeHunted){
        this.huntOrBeHunted = huntOrBeHunted;
        spawnLocations = new ArrayList<>();
        resetSpawnLocations();
        runTaskTimer(huntOrBeHunted,100,100);
    }

    @Override
    public void run() {
        int ran = ThreadLocalRandom.current().nextInt(0,spawnLocations.size());
        ArmorStand armorStand = (ArmorStand) huntOrBeHunted.getArena().getWorld().spawnEntity(spawnLocations.get(ran), EntityType.ARMOR_STAND);

        Location pos1 = new Location(Bukkit.getWorld("world"),spawnLocations.get(ran).getX(),spawnLocations.get(ran).getY()-1,spawnLocations.get(ran).getZ());

        Location pos2 = spawnLocations.get(ran).subtract(1,0,1);
        pos2.setY(6);

        Cuboid cuboid = new Cuboid(pos2,pos1);
        spawnLocations.remove(ran);

        armorStand.setInvisible(true);
        armorStand.setBasePlate(false);
        armorStand.setInvulnerable(true);

        armorStand.getEquipment().setHelmet(new ItemStack(Material.FEATHER));

        huntOrBeHunted.getArena().getGame().getPowerUps().put(cuboid,armorStand);

        if (spawnLocations.isEmpty()){
            resetSpawnLocations();
        }
    }

    public void resetSpawnLocations(){
        for (String str : huntOrBeHunted.getConfig().getConfigurationSection("arena.hider-spawns.").getKeys(false)){
            spawnLocations.add(new Location(
                    Bukkit.getWorld(huntOrBeHunted.getConfig().getString("arena.hider-spawns." + str + ".world")),
                    huntOrBeHunted.getConfig().getDouble("arena.hider-spawns." + str + ".x"),
                    huntOrBeHunted.getConfig().getDouble("arena.hider-spawns." + str + ".y"),
                    huntOrBeHunted.getConfig().getDouble("arena.hider-spawns." + str + ".z"),
                    (float) huntOrBeHunted.getConfig().getDouble("arena.hider-spawns." + str + ".yaw"),
                    (float) huntOrBeHunted.getConfig().getDouble("arena.hider-spawns." + str + ".pitch")
            ));
        }
    }
}
