package yio.tro.opacha.menu;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

public class LanguagesManager {
    private static LanguagesManager _instance = null;

    private static final String LANGUAGES_FILE = "languages.xml";
    private static final String DEFAULT_LANGUAGE = "en_UK";

    //private HashMap<String, HashMap<String, String>> _strings = null;
    private HashMap<String, String> _language = null;
    private String _languageName = null;


    private LanguagesManager() {
        // Create language map
        _language = new HashMap<String, String>();

        // Try to load system language
        // If it fails, fallback to default language
        _languageName = java.util.Locale.getDefault().toString();
        if (!loadLanguage(_languageName)) {
            loadLanguage(DEFAULT_LANGUAGE);
            _languageName = DEFAULT_LANGUAGE;
        }
    }


    public static void initialize() {
        _instance = null;
    }


    public static LanguagesManager getInstance() {
        if (_instance == null) {
            _instance = new LanguagesManager();
        }

        return _instance;
    }


    public String getLanguage() {
        return _languageName;
    }


    public void setLanguage(String langName) {
        loadLanguage(langName);
        _languageName = langName;
    }


    public String getString(String key) {
        String string;

        if (_language != null) {
            // Look for string in selected language
            string = _language.get(key);

            if (string != null) {
                return string;
            }
        }

        // Key not found, return the key itself
        return key;
    }


    public String getString(String key, Object... args) {
        return String.format(getString(key), args);
    }


    public ArrayList<LanguageChooseItem> getChooseListItems() {
        ArrayList<LanguageChooseItem> result = new ArrayList<>();

        try {
            for (XmlReader.Element language : readLanguageNodes()) {
                LanguageChooseItem chooseItem = new LanguageChooseItem();
                chooseItem.name = language.getAttribute("name");
                chooseItem.title = language.getAttribute("title");
                chooseItem.author = language.getAttribute("author");

                result.add(chooseItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public boolean loadLanguage(String languageName) {
        try {
            for (XmlReader.Element languageNode : readLanguageNodes()) {
                if (!isTargetLanguageNode(languageName, languageNode)) continue;
                _language.clear();
                for (XmlReader.Element stringNode : languageNode.getChildrenByName("string")) {
                    String key = stringNode.getAttribute("key");
                    String value = stringNode.getAttribute("value");
                    value = value.replace("<br />", "\n");
                    _language.put(key, value);
                }

                return true;
            }
        } catch (Exception e) {
            System.out.println("Error loading languages file " + LANGUAGES_FILE);
            return false;
        }

        return false;
    }


    private Array<XmlReader.Element> readLanguageNodes() {
        FileHandle fileHandle = Gdx.files.internal(LANGUAGES_FILE);
        XmlReader.Element root = new XmlReader().parse(decodeText(fileHandle.readBytes()));
        return root.getChildrenByName("language");
    }


    private String decodeText(byte[] bytes) {
        // languages.xml is stored as UTF-16 with BOM, decode manually to avoid relying on charset support
        if (bytes.length >= 2 && (bytes[0] & 0xFF) == 0xFF && (bytes[1] & 0xFF) == 0xFE) {
            return decodeUtf16(bytes, false);
        }
        if (bytes.length >= 2 && (bytes[0] & 0xFF) == 0xFE && (bytes[1] & 0xFF) == 0xFF) {
            return decodeUtf16(bytes, true);
        }
        String text = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
        if (text.length() > 0 && text.charAt(0) == '\uFEFF') {
            text = text.substring(1);
        }
        return text;
    }


    private String decodeUtf16(byte[] bytes, boolean bigEndian) {
        char[] chars = new char[(bytes.length - 2) / 2];
        for (int i = 0; i < chars.length; i++) {
            int a = bytes[2 + 2 * i] & 0xFF;
            int b = bytes[3 + 2 * i] & 0xFF;
            chars[i] = (char) (bigEndian ? (a << 8) | b : (b << 8) | a);
        }
        return new String(chars);
    }


    private boolean isTargetLanguageNode(String langName, XmlReader.Element node) {
        String name = node.getAttribute("name");
        String secondName = node.getAttribute("second_name", "-");
        return langName.equals(name) || langName.equals(secondName);
    }
}
