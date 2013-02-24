package me.Hoot215.TheWalls2;

public class TheWalls2AutoGameStartTimer implements Runnable{
	private TheWalls2 plugin;
	private long initialTime;
	private long normalTime;

	public TheWalls2AutoGameStartTimer(TheWalls2 instance,long initial,long normal){
		plugin=instance;
		initialTime=initial;
		normalTime=normal;
	}

	public void run(){
		try{
			Thread.sleep(initialTime*60000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}

		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,new Runnable(){
			public void run(){
				TheWalls2GameList gameList=plugin.getGameList();
				if (gameList==null&&!TheWalls2World.isRestoring&&plugin.startGame())
					System.out.println("[UltraWalls] Game started automatically");
			}
		});

		while(true){
			try{
				Thread.sleep(normalTime*60000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}

			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,new Runnable(){
				public void run(){
					TheWalls2GameList gameList=plugin.getGameList();
					if (gameList==null&&!TheWalls2World.isRestoring&&plugin.startGame())
						System.out.println("[UltraWalls] Game started automatically");
				}
			});
		}
	}
}
