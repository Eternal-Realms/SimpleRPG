```java
// This is just code I want to keep around encase I want to use it in the future.

public class weirdExplosion {
	public void executeWeirdExplosion() {
		new BukkitRunnable() {
			int count = 0;
			Vector movement = null;
			List<Block> blocks = getNearbyBlocks(creeper.getLocation(), 15);
			List<FallingBlock> fallingBlocks = new ArrayList<>();

			// 20 Ticks * wanted seconds;
			int maxTime = 300;

			public void run() {
				if (count == maxTime) {
					cancel();

					creeper.remove();
					for (FallingBlock fallingBlock : fallingBlocks) {
						fallingBlock.setGravity(true);
					}

					return;
				}

				Random r = new Random();
				Block block = blocks.get(r.nextInt(blocks.size()));

				FallingBlock fallingBlock = creeper.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData());
				fallingBlock.setVelocity((fallingBlock.getLocation().toVector().subtract(creeper.getLocation().toVector())
					.multiply(-10).normalize()));

				fallingBlock.setGravity(false);
				fallingBlock.setDropItem(false);
				fallingBlock.setHurtEntities(true);

				fallingBlocks.add(fallingBlock);

				count++;

			}
		}.runTaskTimer(SimpleRPG.getPlugin(), 0, 0);
	}
}

```

```java
public class save {
	public static void start() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(SimpleRPG.getPlugin(), () -> {
			for (RPGPlayer rpgPlayer : RPGPlayerManager.get()) {
				if (rpgPlayer.getPlayer().isOnline()) {
					rpgPlayer.save();
				}
			}
		}, 0L, 600L);
	}
}


```