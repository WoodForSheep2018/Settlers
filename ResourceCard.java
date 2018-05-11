
public class ResourceCard extends Card {

	private Hex.Resource resource;

	public ResourceCard(Hex.Resource r) {
		resource = r;
	}
	
	public boolean isResource(int r) {
		TerrainHex.Resource[] resources = Hex.Resource.values();
		return resources[r]==resource;
	}
	
	public void play() {
		
	}

}
