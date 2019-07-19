package fr.nathanael2611.roleplaychat.plugin.core;

import org.json.JSONObject;
import fr.nathanael2611.roleplaychat.plugin.RolePlayChat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class RolePlayNames {
    public static JSONObject getAllRPChatFile() {
        String json = readFileToString(RolePlayChat.getRoleplayNamesFile());
        if(!json.startsWith("{") && !json.endsWith("}")){
            try {
                final FileWriter writer = new FileWriter(RolePlayChat.getRoleplayNamesFile());
                writer.write("{}");
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new JSONObject(readFileToString(RolePlayChat.getRoleplayNamesFile()));
    }

    public static void setRPName(final String player, final String rpName) {
        final JSONObject jsonObject = getAllRPChatFile();
        jsonObject.put(player, rpName);
        try {
            final FileWriter writer = new FileWriter(RolePlayChat.getRoleplayNamesFile());
            writer.write(jsonObject.toString());
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRPName(final String player) {
        final JSONObject jsonObject = getAllRPChatFile();
        if (jsonObject.isNull(player)) {
            return player;
        }
        return jsonObject.getString(player);
    }

    public static String readFileToString(final File file) {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            final String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            reader.close();
            return stringBuilder.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

}
