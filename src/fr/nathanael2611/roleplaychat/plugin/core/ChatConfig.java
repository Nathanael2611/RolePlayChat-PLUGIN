package fr.nathanael2611.roleplaychat.plugin.core;

import com.sun.org.apache.bcel.internal.generic.NOP;
import fr.nathanael2611.roleplaychat.plugin.RolePlayChat;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class ChatConfig {

    private ChatType hrp;
    private ChatType normal;
    private ChatType shout;
    private ChatType whisp;
    private ChatType action;

    private String noperm_template;
    private String setrpname_template;
    private String setrpname_other_template;

    private ChatType[] chatTypes;

    private final File configFile;

    private Properties properties = new Properties();

    private FileInputStream input;

    public ChatConfig(File configFile) throws Exception {
        this.configFile = configFile;
        reload();
        chatTypes = new ChatType[]{
                hrp, normal, shout, whisp, action
        };
    }

    public void reload() throws Exception {
        input = new FileInputStream(configFile);
        properties.load(
                new InputStreamReader(input, Charset.forName("UTF-8"))
        );

        createPropertyIfNull("prefix.hrp", "(");
        createPropertyIfNull("prefix.normal", "");
        createPropertyIfNull("prefix.shout", "!");
        createPropertyIfNull("prefix.whisp", "$");
        createPropertyIfNull("prefix.action", "*");

        createPropertyIfNull("distance.hrp", "0");
        createPropertyIfNull("distance.normal", "15");
        createPropertyIfNull("distance.shout", "30");
        createPropertyIfNull("distance.whisp", "2");
        createPropertyIfNull("distance.action", "15");

        createPropertyIfNull("format.hrp", "§7[HRP] {player} ({rpname}) : {message}");
        createPropertyIfNull("format.normal", "[RP] {rpname} : {message}");
        createPropertyIfNull("format.shout", "§c[RP-SHOUT] {rpname} : {message}");
        createPropertyIfNull("format.whisp", "§2[RP-WHISP] {rpname} : {message}");
        createPropertyIfNull("format.action", "§a1§o{rpname} {message}");

        createPropertyIfNull("message.nopermission", "§cVous n'avez pas la permission.");
        createPropertyIfNull("message.setrpname", "§aVotre nom rp a été défini à {rpname}");
        createPropertyIfNull("message.setrpname.other", "§aLe nom rp de {player] a été défini à {rpname}");

        hrp = new ChatType(
                properties.getProperty("prefix.hrp"),
                Integer.parseInt(properties.getProperty("distance.hrp")),
                properties.getProperty("format.hrp")
        );
        normal = new ChatType(
                properties.getProperty("prefix.normal"),
                Integer.parseInt(properties.getProperty("distance.normal")),
                properties.getProperty("format.normal")
        );
        shout = new ChatType(
                properties.getProperty("prefix.shout"),
                Integer.parseInt(properties.getProperty("distance.shout")),
                properties.getProperty("format.shout")
        );
        whisp = new ChatType(
                properties.getProperty("prefix.whisp"),
                Integer.parseInt(properties.getProperty("distance.whisp")),
                properties.getProperty("format.whisp")
        );
        action = new ChatType(
                properties.getProperty("prefix.action"),
                Integer.parseInt(properties.getProperty("distance.action")),
                properties.getProperty("format.action")
        );

        noperm_template = properties.getProperty("message.nopermission");
        setrpname_template = properties.getProperty("message.setrpname");
        setrpname_other_template = properties.getProperty("message.setrpname.other");

        properties.store(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8"), "");
        input.close();
    }

    public void createPropertyIfNull(String key, String value) {
        if(properties.getProperty(key) == null){
            properties.setProperty(key, value);
        }
    }

    public ChatType getChatType(EnumCHatTypes type){
        switch (type){
            case HRP: return hrp;
            case SHOUT: return shout;
            case WISPH: return whisp;
            case ACTION: return action;
            case NORMAL: return normal;
        }
        return normal;
    }

    public String getMessageTemplate(EnumMessages msgTemplate){
        switch (msgTemplate){
            case NOPERM:return noperm_template;
            case SETRPNAME:return setrpname_template;
            case SETRPNAMEOTHER:return setrpname_other_template;



        }
        return "";
    }

    public ChatType[] getChatTypes() {
        return chatTypes;
    }
}
