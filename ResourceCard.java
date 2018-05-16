
public class ResourceCard {

	private TerrainHex.Resource resource;

	public ResourceCard(TerrainHex.Resource r) {
		resource = r;
	}
	
	public boolean isResource(int r) {
		TerrainHex.Resource[] resources = TerrainHex.Resource.values();
		return resources[r]==resource;
	}

}
