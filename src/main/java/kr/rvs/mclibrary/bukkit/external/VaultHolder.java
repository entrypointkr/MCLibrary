package kr.rvs.mclibrary.bukkit.external;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-10-31.
 */
public class VaultHolder extends Permission implements Economy { // Chat abstract?
    private static final VaultHolder INSTANCE = new VaultHolder();

    public static VaultHolder get() {
        return INSTANCE;
    }

    private Permission getPermission() {
        return Bukkit.getServicesManager().load(Permission.class);
    }

    private Economy getEconomy() {
        return Bukkit.getServicesManager().load(Economy.class);
    }

    @Override
    public String getName() {
        return getPermission().getName();
    }

    @Override
    public boolean hasBankSupport() {
        return getEconomy().hasBankSupport();
    }

    @Override
    public int fractionalDigits() {
        return getEconomy().fractionalDigits();
    }

    @Override
    public String format(double v) {
        return getEconomy().format(v);
    }

    @Override
    public String currencyNamePlural() {
        return getEconomy().currencyNamePlural();
    }

    @Override
    public String currencyNameSingular() {
        return getEconomy().currencyNameSingular();
    }

    @Override
    @Deprecated
    public boolean hasAccount(String s) {
        return getEconomy().hasAccount(s);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return getEconomy().hasAccount(offlinePlayer.getName());
    }

    @Override
    @Deprecated
    public boolean hasAccount(String s, String s1) {
        return getEconomy().hasAccount(s, s1);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return getEconomy().hasAccount(offlinePlayer.getName(), s);
    }

    @Override
    @Deprecated
    public double getBalance(String s) {
        return getEconomy().getBalance(s);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return getEconomy().getBalance(offlinePlayer.getName());
    }

    @Override
    @Deprecated
    public double getBalance(String s, String s1) {
        return getEconomy().getBalance(s, s1);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getEconomy().getBalance(offlinePlayer.getName(), s);
    }

    @Override
    @Deprecated
    public boolean has(String s, double v) {
        return getEconomy().has(s, v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return getEconomy().has(offlinePlayer.getName(), v);
    }

    @Override
    @Deprecated
    public boolean has(String s, String s1, double v) {
        return getEconomy().has(s, s1, v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return getEconomy().has(offlinePlayer.getName(), s, v);
    }

    @Override
    @Deprecated
    public EconomyResponse withdrawPlayer(String s, double v) {
        return getEconomy().withdrawPlayer(s, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return getEconomy().withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    @Deprecated
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return getEconomy().withdrawPlayer(s, s1, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return getEconomy().withdrawPlayer(offlinePlayer.getName(), s, v);
    }

    @Override
    @Deprecated
    public EconomyResponse depositPlayer(String s, double v) {
        return getEconomy().depositPlayer(s, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return getEconomy().depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    @Deprecated
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return getEconomy().depositPlayer(s, s1, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return getEconomy().depositPlayer(offlinePlayer.getName(), s, v);
    }

    @Override
    @Deprecated
    public EconomyResponse createBank(String s, String s1) {
        return getEconomy().createBank(s, s1);
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return getEconomy().createBank(s, offlinePlayer);
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return getEconomy().deleteBank(s);
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return getEconomy().bankBalance(s);
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return getEconomy().bankHas(s, v);
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return getEconomy().bankWithdraw(s, v);
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return getEconomy().bankDeposit(s, v);
    }

    @Override
    @Deprecated
    public EconomyResponse isBankOwner(String s, String s1) {
        return getEconomy().isBankOwner(s, s1);
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return getEconomy().isBankOwner(s, offlinePlayer);
    }

    @Override
    @Deprecated
    public EconomyResponse isBankMember(String s, String s1) {
        return getEconomy().isBankMember(s, s1);
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return getEconomy().isBankMember(s, offlinePlayer);
    }

    @Override
    public List<String> getBanks() {
        return getEconomy().getBanks();
    }

    @Override
    @Deprecated
    public boolean createPlayerAccount(String s) {
        return getEconomy().createPlayerAccount(s);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return getEconomy().createPlayerAccount(offlinePlayer);
    }

    @Override
    @Deprecated
    public boolean createPlayerAccount(String s, String s1) {
        return getEconomy().createPlayerAccount(s, s1);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return getEconomy().createPlayerAccount(offlinePlayer, s);
    }

    @Override
    public boolean isEnabled() {
        return getPermission().isEnabled();
    }

    @Override
    public boolean hasSuperPermsCompat() {
        return getPermission().hasSuperPermsCompat();
    }

    @Override
    @Deprecated
    public boolean has(String world, String player, String permission) {
        return getPermission().has(world, player, permission);
    }

    @Override
    @Deprecated
    public boolean has(World world, String player, String permission) {
        return getPermission().has(world, player, permission);
    }

    @Override
    public boolean has(CommandSender sender, String permission) {
        return getPermission().has(sender, permission);
    }

    @Override
    public boolean has(Player player, String permission) {
        return getPermission().has(player, permission);
    }

    @Override
    @Deprecated
    public boolean playerHas(String s, String s1, String s2) {
        return getPermission().playerHas(s, s1, s2);
    }

    @Override
    @Deprecated
    public boolean playerHas(World world, String player, String permission) {
        return getPermission().playerHas(world, player, permission);
    }

    @Override
    public boolean playerHas(String world, OfflinePlayer player, String permission) {
        return getPermission().playerHas(world, player, permission);
    }

    @Override
    public boolean playerHas(Player player, String permission) {
        return getPermission().playerHas(player, permission);
    }

    @Override
    @Deprecated
    public boolean playerAdd(String s, String s1, String s2) {
        return getPermission().playerAdd(s, s1, s2);
    }

    @Override
    @Deprecated
    public boolean playerAdd(World world, String player, String permission) {
        return getPermission().playerAdd(world, player, permission);
    }

    @Override
    public boolean playerAdd(String world, OfflinePlayer player, String permission) {
        return getPermission().playerAdd(world, player, permission);
    }

    @Override
    public boolean playerAdd(Player player, String permission) {
        return getPermission().playerAdd(player, permission);
    }

    @Override
    @Deprecated
    public boolean playerAddTransient(String player, String permission) throws UnsupportedOperationException {
        return getPermission().playerAddTransient(player, permission);
    }

    @Override
    public boolean playerAddTransient(OfflinePlayer player, String permission) throws UnsupportedOperationException {
        return getPermission().playerAddTransient(player, permission);
    }

    @Override
    public boolean playerAddTransient(Player player, String permission) {
        return getPermission().playerAddTransient(player, permission);
    }

    @Override
    public boolean playerAddTransient(String worldName, OfflinePlayer player, String permission) {
        return getPermission().playerAddTransient(worldName, player, permission);
    }

    @Override
    public boolean playerAddTransient(String worldName, Player player, String permission) {
        return getPermission().playerAddTransient(worldName, player, permission);
    }

    @Override
    @Deprecated
    public boolean playerAddTransient(String worldName, String player, String permission) {
        return getPermission().playerAddTransient(worldName, player, permission);
    }

    @Override
    @Deprecated
    public boolean playerRemoveTransient(String worldName, String player, String permission) {
        return getPermission().playerRemoveTransient(worldName, player, permission);
    }

    @Override
    public boolean playerRemoveTransient(String worldName, OfflinePlayer player, String permission) {
        return getPermission().playerRemoveTransient(worldName, player, permission);
    }

    @Override
    public boolean playerRemoveTransient(String worldName, Player player, String permission) {
        return getPermission().playerRemoveTransient(worldName, player, permission);
    }

    @Override
    @Deprecated
    public boolean playerRemove(String s, String s1, String s2) {
        return getPermission().playerRemove(s, s1, s2);
    }

    @Override
    public boolean playerRemove(String world, OfflinePlayer player, String permission) {
        return getPermission().playerRemove(world, player, permission);
    }

    @Override
    @Deprecated
    public boolean playerRemove(World world, String player, String permission) {
        return getPermission().playerRemove(world, player, permission);
    }

    @Override
    public boolean playerRemove(Player player, String permission) {
        return getPermission().playerRemove(player, permission);
    }

    @Override
    @Deprecated
    public boolean playerRemoveTransient(String player, String permission) {
        return getPermission().playerRemoveTransient(player, permission);
    }

    @Override
    public boolean playerRemoveTransient(OfflinePlayer player, String permission) {
        return getPermission().playerRemoveTransient(player, permission);
    }

    @Override
    public boolean playerRemoveTransient(Player player, String permission) {
        return getPermission().playerRemoveTransient(player, permission);
    }

    @Override
    public boolean groupHas(String s, String s1, String s2) {
        return getPermission().groupHas(s, s1, s2);
    }

    @Override
    public boolean groupHas(World world, String group, String permission) {
        return getPermission().groupHas(world, group, permission);
    }

    @Override
    public boolean groupAdd(String s, String s1, String s2) {
        return getPermission().groupAdd(s, s1, s2);
    }

    @Override
    public boolean groupAdd(World world, String group, String permission) {
        return getPermission().groupAdd(world, group, permission);
    }

    @Override
    public boolean groupRemove(String s, String s1, String s2) {
        return getPermission().groupRemove(s, s1, s2);
    }

    @Override
    public boolean groupRemove(World world, String group, String permission) {
        return getPermission().groupRemove(world, group, permission);
    }

    @Override
    @Deprecated
    public boolean playerInGroup(String s, String s1, String s2) {
        return getPermission().playerInGroup(s, s1, s2);
    }

    @Override
    @Deprecated
    public boolean playerInGroup(World world, String player, String group) {
        return getPermission().playerInGroup(world, player, group);
    }

    @Override
    public boolean playerInGroup(String world, OfflinePlayer player, String group) {
        return getPermission().playerInGroup(world, player, group);
    }

    @Override
    public boolean playerInGroup(Player player, String group) {
        return getPermission().playerInGroup(player, group);
    }

    @Override
    @Deprecated
    public boolean playerAddGroup(String s, String s1, String s2) {
        return getPermission().playerAddGroup(s, s1, s2);
    }

    @Override
    @Deprecated
    public boolean playerAddGroup(World world, String player, String group) {
        return getPermission().playerAddGroup(world, player, group);
    }

    @Override
    public boolean playerAddGroup(String world, OfflinePlayer player, String group) {
        return getPermission().playerAddGroup(world, player, group);
    }

    @Override
    public boolean playerAddGroup(Player player, String group) {
        return getPermission().playerAddGroup(player, group);
    }

    @Override
    @Deprecated
    public boolean playerRemoveGroup(String s, String s1, String s2) {
        return getPermission().playerRemoveGroup(s, s1, s2);
    }

    @Override
    @Deprecated
    public boolean playerRemoveGroup(World world, String player, String group) {
        return getPermission().playerRemoveGroup(world, player, group);
    }

    @Override
    public boolean playerRemoveGroup(String world, OfflinePlayer player, String group) {
        return getPermission().playerRemoveGroup(world, player, group);
    }

    @Override
    public boolean playerRemoveGroup(Player player, String group) {
        return getPermission().playerRemoveGroup(player, group);
    }

    @Override
    @Deprecated
    public String[] getPlayerGroups(String s, String s1) {
        return getPermission().getPlayerGroups(s, s1);
    }

    @Override
    @Deprecated
    public String[] getPlayerGroups(World world, String player) {
        return getPermission().getPlayerGroups(world, player);
    }

    @Override
    public String[] getPlayerGroups(String world, OfflinePlayer player) {
        return getPermission().getPlayerGroups(world, player);
    }

    @Override
    public String[] getPlayerGroups(Player player) {
        return getPermission().getPlayerGroups(player);
    }

    @Override
    @Deprecated
    public String getPrimaryGroup(String s, String s1) {
        return getPermission().getPrimaryGroup(s, s1);
    }

    @Override
    @Deprecated
    public String getPrimaryGroup(World world, String player) {
        return getPermission().getPrimaryGroup(world, player);
    }

    @Override
    public String getPrimaryGroup(String world, OfflinePlayer player) {
        return getPermission().getPrimaryGroup(world, player);
    }

    @Override
    public String getPrimaryGroup(Player player) {
        return getPermission().getPrimaryGroup(player);
    }

    @Override
    public String[] getGroups() {
        return getPermission().getGroups();
    }

    @Override
    public boolean hasGroupSupport() {
        return getPermission().hasGroupSupport();
    }
}
