package ua.lokha.spigotloggerforbungee.utils;


import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * Конфиг формата .yml
 */
@SuppressWarnings("Duplicates")
public class Config {

	public static final String                SEP      = File.separator; // separator - длинное слово!!
	private static      ConfigurationProvider provider = YamlConfiguration.getProvider(YamlConfiguration.class);
	private File          file;
	private Configuration configuration;
	private boolean first = false;

	private String description = "";

	/**
	 * @param plugin плагин
	 * @param file   путь, относительно директории плагина
	 */
	public Config(Plugin plugin, String file) {
		this.file = new File(plugin.getDataFolder() + SEP + file);
		this.reload();
	}

	/**
	 * Загрузить конфиг
	 * @param file файл конфига
	 */
	public Config(File file, boolean load) {
		this.file = file;
		if(load) {
			this.reload();
		}
	}

	public Config(File file) {
		this(file, true);
	}

    public File getFile() {
        return file;
    }

    public void save() {
		try {
			FileOutputStream stream = new FileOutputStream(file);
			if(!description.isEmpty()) {
				stream.write(('#' + description.replace("\n", "\n#") + "\n").getBytes());
			}
			Writer writer = new OutputStreamWriter(stream);
			provider.save(this.configuration, writer);
			writer.close();
			stream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void reload() {
		if(file == null) {
			throw new NullPointerException("конфиг не был загружен с файла, сохранить его нельзя");
		}
		this.createFile();
		this.loadFromFile();
	}

	protected void loadFromFile() {
		try {
			String content = this.loadDataFromFile();
			StringBuilder dataBuild = new StringBuilder();
			StringBuilder descBuild = new StringBuilder();
			for(String line : content.split("\n")) {
				if(!line.isEmpty()) {
					if(line.charAt(0) == '#') {
						descBuild.append(line.substring(1)).append("\n");
					} else {
						dataBuild.append(line).append("\n");
					}
				}
			}

			this.description = StringUtils.substring(descBuild.toString(), 0, -1);
			this.configuration = provider.load(dataBuild.toString());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	protected String loadDataFromFile() throws IOException {
		FileInputStream stream = new FileInputStream(file);
		byte[] bytes = new byte[stream.available()];
		stream.read(bytes);
		stream.close();
		return new String(bytes);
	}

	protected void createFile() {
		if(!file.exists()) {
			try {
				first = true;
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			first = false;
		}
	}

	/**
	 * Вставить комментарий кофига<br>
	 * Он будет виден в самом верху конфига<br>
	 * @param description строка с описанием, можно использовать переносы - '\n'
	 */
	public void setDescription(String description) {
		if(!this.description.equals(description)) {
			this.description = description;
			this.save();
		}
	}

	/*public void reload() {
		if (!file.exists()) {
			try {
				first = true;
				plugin.getDataFolder().mkdir(); //Лешка, такую штуку лучше не забывать :D
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			first = false;
		}

		try {
			this.configuration = provider.load(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}*/

	public Configuration getCfg() {
		return this.configuration;
	}

	/**
	 * Установить сначение, если его не существует
	 * @param path
	 * @param value
	 */
	public void setIfNotExist(String path, Object value) {
		if(!this.contains(path)) {
			this.setAndSave(path, value);
		}
	}

	/**
	 * Проверить наличие параметра в конфиге
	 * @param path
	 * @return
	 */
	public boolean contains(String path) {
		return this.configuration.get(path) != null;
	}

	public void setDefault(String path, Object value) {
		if(first) {
			this.setAndSave(path, value);
		}

	}

	public Collection<String> getKeys() {
		return this.configuration.getKeys();
	}

	public List<String> getStringList(String path) {
		return this.configuration.getStringList(path);
	}

	/**
	 * Редактировать и сохранить конфиг
	 * @param path
	 * @param value
	 */
	public void setAndSave(String path, Object value) {
		this.configuration.set(path, value);
		this.save();
	}

	/**
	 * Получить или вставить новое значение в конфиг
	 * @param path путь
	 * @param def  значение по уполчанию
	 * @param <T>
	 * @return значние их конфига или значение по умолчанию
	 */
	@SuppressWarnings("unchecked")
	public <T> T getOrSet(String path, T def) {
		if(!this.contains(path)) {
			this.setAndSave(path, def);
			return def;
		} else {
			return (T) this.configuration.get(path);
		}
	}

	public String getStringColor(String path) {
		String line = this.configuration.getString(path);
		if(line == null) {
			return null;
		}
		line = StringUtils.replace(line, "&", "§");
		line = StringUtils.replace(line, "\\n", "\n");
		return line;
	}

	/**
	 * Создать секцию, если ее нету
	 * @param path
	 */
	public void createSectionIfNotExist(String path) {
		this.setIfNotExist(path, new LinkedHashMap<>());
	}

	/**
	 * Генерировать свободный путь
	 * @param path путь, к которому будем генерировать новую секцию
	 */
	public int generateNumberPath(String path) {
		for(int i = 0; ; i++) {
			if(!this.contains(path + "." + i)) {
				return i;
			}
		}
	}

	public String getString(String path) {
		return this.configuration.getString(path);
	}

	public int getInt(String path) {
		return configuration.getInt(path);
	}

	public boolean getBoolean(String path) {
		return configuration.getBoolean(path);
	}

	public Configuration getSection(String path) {
		return this.configuration.getSection(path);
	}

	/**
	 * Установить значение
	 * @param path
	 * @param value
	 */
	public void set(String path, Object value) {
		this.configuration.set(path, value);
	}

	public long getLong(String s) {
		return this.configuration.getLong(s);
	}

	public Set<String> getKeys(String path) {
		this.createSectionIfNotExist(path);
		return new HashSet<>(this.getSection(path).getKeys());
	}
}

