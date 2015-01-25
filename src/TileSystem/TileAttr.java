package TileSystem;

public enum TileAttr {
	NONE, HUT, BOAT, CORPSE, PLANE, FIRE, RELIGIOUS_ARTIFACT, ALIEN_ARTIFACT, TREE, SHRUB, PALM_TREE;
	
	public String toLetter() {
		if(this != NONE) {
			return this.name().substring(0, 1);
		}
		return "";
	}
}
