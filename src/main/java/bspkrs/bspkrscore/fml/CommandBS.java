package bspkrs.bspkrscore.fml;

import java.util.ArrayList;
import java.util.List;

import bspkrs.util.ModVersionChecker;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

@SuppressWarnings("unchecked")
public class CommandBS extends CommandBase
{
    @SuppressWarnings("rawtypes")
    private static List version = new ArrayList();

    static
    {
        version.add("version");
    }

    @Override
    public String getCommandName()
    {
        return "bs";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.bs.usage";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender par1ICommandSender)
    {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 1;
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public List getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
    {
        return args.length == 2 ? getListOfStringsMatchingLastWord(args, ModVersionChecker.getVersionCheckerMap().keySet().toArray(new String[] {})) : args.length == 1 ? version : null;
    }

    @Override
    public int compareTo(ICommand object)
    {
        if (object instanceof CommandBase)
            return this.getCommandName().compareTo(((CommandBase) object).getCommandName());

        return 0;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (!bspkrsCoreMod.instance.allowUpdateCheck)
            throw new WrongUsageException("commands.bs.disabled");

        if (args.length != 2)
            throw new WrongUsageException("commands.bs.usage");

        if (!args[0].equalsIgnoreCase("version"))
            throw new WrongUsageException("commands.bs.usage");

        String[] message = ModVersionChecker.checkVersionForMod(args[1]);

        for (String s : message)
            sender.addChatMessage(new TextComponentString(s));
	}
}
