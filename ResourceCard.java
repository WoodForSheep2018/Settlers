
public class ResourceCard {

	private Hex.Resource resource;

	public ResourceCard(Hex.Resource r) {
		resource = r;
	}
	
	public boolean isResource(Hex.Resource r) {
		TerrainHex.Resource[] resources = Hex.Resource.values();
		return resources[r]==resource;
	}

}
