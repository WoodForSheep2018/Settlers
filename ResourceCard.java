
public class ResourceCard {

	private Hex.Resource resource;

	public ResourceCard(Hex.Resource r) {
		resource = r;
	}
	
	public boolean isResource(int r) {
		TerrainHex.Resource[] resources = TerrainHex.Resource.values();
		return resources[r]==resource;
	}

}
