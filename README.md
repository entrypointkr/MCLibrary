[![Build Status](https://travis-ci.org/DevCrafters/MCLibrary.svg?branch=master)](https://travis-ci.org/DevCrafters/MCLibrary)

# MCLibrary

Jenkins Build Server: http://builds.rvs.kr/job/MCLibrary/

## Command

```java
@Command(
	args = "test"
)
static class TestCommand {
	@Command(
		args = "first"
	)
	public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
		wrapper.sendMessage("Example command 1");
	}
  
	@Command(
		args = "second"
	)
	public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
		wrapper.sendMessage("Example command 2");
	}
}
```

## GUI
```java
new GUI(
	new GUISignatureAdapter(InventoryType.CHEST)
			.title("MCLibrary GUI")
			.item(13, new ItemBuilder(Material.MAP).display("MCLibrary").build()),
	new EventCancelHandler(),
	new SpecificSlotHandler(13) {
		@Override
		public void receive(GUIClickEvent e) {
			e.sendMessage(
					"Hello,",
					"MCLibrary"
			);
		}
	}
).open(player);
```
