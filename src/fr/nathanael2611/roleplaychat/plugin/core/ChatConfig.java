package fr.nathanael2611.roleplaychat.plugin.core;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class ChatConfig
{

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

    public ChatConfig(File configFile) throws Exception
    {
        this.configFile = configFile;
        reload();
        chatTypes = new ChatType[]{hrp, normal, shout, whisp, action};
    }

    public void reload() throws Exception
    {
        this.input = new FileInputStream(configFile);
        this.properties.load(new InputStreamReader(input, Charset.forName("UTF-8")));

        this.createPropertyIfNull("prefix.hrp", "(");
        this.createPropertyIfNull("prefix.normal", "");
        this.createPropertyIfNull("prefix.shout", "!");
        this.createPropertyIfNull("prefix.whisp", "$");
        this.createPropertyIfNull("prefix.action", "*");

        this.createPropertyIfNull("distance.hrp", "0");
        this.createPropertyIfNull("distance.normal", "15");
        this.createPropertyIfNull("distance.shout", "30");
        this.createPropertyIfNull("distance.whisp", "2");
        this.createPropertyIfNull("distance.action", "15");

        this.createPropertyIfNull("format.hrp", "§7[HRP] {player} ({rpname}) : {message}");
        this.createPropertyIfNull("format.normal", "[RP] {rpname} : {message}");
        this.createPropertyIfNull("format.shout", "§c[RP-SHOUT] {rpname} : {message}");
        this.createPropertyIfNull("format.whisp", "§2[RP-WHISP] {rpname} : {message}");
        this.createPropertyIfNull("format.action", "§a1§o{rpname} {message}");

        this.createPropertyIfNull("message.nopermission", "§cVous n'avez pas la permission.");
        this.createPropertyIfNull("message.setrpname", "§aVotre nom rp a été défini à {rpname}");
        this.createPropertyIfNull("message.setrpname.other", "§aLe nom rp de {player} a été défini à {rpname}");

        this.hrp = new ChatType(properties.getProperty("prefix.hrp"), Integer.parseInt(properties.getProperty("distance.hrp")), properties.getProperty("format.hrp"));
        this.normal = new ChatType(properties.getProperty("prefix.normal"), Integer.parseInt(properties.getProperty("distance.normal")), properties.getProperty("format.normal"));
        this.shout = new ChatType(properties.getProperty("prefix.shout"), Integer.parseInt(properties.getProperty("distance.shout")), properties.getProperty("format.shout"));
        this.whisp = new ChatType(properties.getProperty("prefix.whisp"), Integer.parseInt(properties.getProperty("distance.whisp")), properties.getProperty("format.whisp"));
        this.action = new ChatType(properties.getProperty("prefix.action"), Integer.parseInt(properties.getProperty("distance.action")), properties.getProperty("format.action"));

        this.noperm_template = properties.getProperty("message.nopermission");
        this.setrpname_template = properties.getProperty("message.setrpname");
        this.setrpname_other_template = properties.getProperty("message.setrpname.other");

        this.properties.store(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8"), "");
        this.input.close();
    }

    public void createPropertyIfNull(String key, String value)
    {
        if (properties.getProperty(key) == null)
        {
            properties.setProperty(key, value);
        }
    }

    public ChatType getChatType(EnumChatTypes type)
    {
        switch (type)
        {
            case HRP:
                return hrp;
            case SHOUT:
                return shout;
            case WISPH:
                return whisp;
            case ACTION:
                return action;
            case NORMAL:
                return normal;
        }
        return normal;
    }

    public String getMessageTemplate(EnumMessages msgTemplate)
    {
        switch (msgTemplate)
        {
            case NOPERM:
                return noperm_template;
            case SETRPNAME:
                return setrpname_template;
            case SETRPNAMEOTHER:
                return setrpname_other_template;
        }
        return "";
    }

    public ChatType[] getChatTypes()
    {
        return chatTypes;
    }
}
