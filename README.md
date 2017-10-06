[![Build Status](https://travis-ci.org/DevCrafters/MCLibrary.svg?branch=master)](https://travis-ci.org/DevCrafters/MCLibrary)

# MCLibrary

Jenkins Build Server: http://builds.rvs.kr/job/MCLibrary/

## Command

```java
@Command(
	args = "test"
)
@SubCommand(TestSubCommand.class)
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
	
	@Command(
		args = "first b"
	)
	public void execute(CommandSenderWrapper wrapper, CommandArguments args) {
		wrapper.sendMessage("Example command 3");
	}
	
	@Command(
		args = "second"
	)
	static class TestSubCommand {
		@Command(
			args = "b"
		)
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
```java
new GUI(
	new GUISignature(InventoryType.CHEST)
			.title("MCLibrary GUI")
			.item(13, new ItemBuilder(Material.MAP).display("MCLibrary").build()),
	new EventCancelHandler(),
	new ClickHandler(13) {
		@Override
		public void click(GUIClickEvent e) {
			e.sendMessage(
				"&aHello,",
				"&eMCLibrary"
			);
		}
	}
).open(player);
```
