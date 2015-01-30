package deserted.model;

public  class Item {
	private String name;
	private String imageName;
	
	public Item(String name, String imageName) {
		this.setName(name);
		this.setImageName(imageName);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
}
