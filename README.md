[![Build Status](https://travis-ci.org/EntryPointKR/MCLibrary.svg?branch=master)](https://travis-ci.org/EntryPointKR/MCLibrary)

# MCLibrary

Jenkins Build Server: http://builds.rvs.kr/job/MCLibrary/

## Command

```java
@Command(args = "test")
static class TestCommand {
	@Command(args = "first")
	public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
		wrapper.sendMessage("Example command 1");
	}
  
	@Command(args = "second")
	public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
		wrapper.sendMessage("Example command 2");
	}
	
	@Command(args = "first b")
	public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
		wrapper.sendMessage("Example command 3");
	}
	
	@Command(args = "second")
	static class TestSubCommand {
		@Command(args = "b")
		public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
			wrapper.sendMessage("Example command 4");
		}
	}
}
```
```
> test first
Example command 1
> test second
Example command 2
> test first b
Example command 3
> test second b
Example command 4
```

## GUI

## usage
```java
new SimpleGUI(new GUIData(InventoryType.CHEST)
        .title("MCLibrary GUI")
        .item(13, new ItemStack(Material.CHEST)))
        .handler(handlers -> handlers.addLast(
                CancelHandler.TOP,
                new ClickHandler().handler(13, event ->
                        GUI.sendMessage(event, "&aHello, MCLibrary"))
        ))
        .open(player);
```

## processors

* **DefaultInventoryProcessor**: Default GUI
* **PagingInventoryProcessor**: Pageable GUI

# ItemStack

## create
```java
ItemStack item = new ItemBuilder(Material.DIAMOND)
        .display("A Diamond")
        .lore(
                "MCLibrary",
                "ItemStack"
        )
        .build();
```

## modify
```java

new ItemWrapper(item)
        .setName("Modified Display")
        .setLore(
                "Modified",
                "Lore"
        );
```

# Events

* PlayerWalkEvent: Fires when player's x or y or z is changed

# ETC

* Cooldown: Cooldown util
* Region: Simple region
* ClickWizard: Get player's clicked pos
* RegionWizard: Get defined region by the player
