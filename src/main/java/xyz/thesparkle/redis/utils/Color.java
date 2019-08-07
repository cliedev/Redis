package xyz.thesparkle.redis.utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class Color {

	public static String translate(String value) {
		return ChatColor.translateAlternateColorCodes('&', value);
	}

	public static List<String> translate(List<String> value) {
		return value.stream().map(Color::translate).collect(Collectors.toList());
	}
}

