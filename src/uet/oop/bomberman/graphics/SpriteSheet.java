package uet.oop.bomberman.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất
 * Class này giúp lấy ra các sprite riêng từ 1 ảnh chung duy nhất đó
 */
public class SpriteSheet {

	private String _path;
	public final int SIZE;
	public int[] _pixels;
	public BufferedImage image;

	public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256);
	public static SpriteSheet evil = new SpriteSheet("/textures/classic2.png", 256);
	public static SpriteSheet gigaTiles = new SpriteSheet("/textures/arnold.png", 16);
	public static SpriteSheet creeper1 = new SpriteSheet("/sprites/creeper1.png", 16);
	public static SpriteSheet creeper2 = new SpriteSheet("/sprites/creeper2.png", 16);
	public static SpriteSheet creeper3 = new SpriteSheet("/sprites/creeper3.png", 16);

	
	public SpriteSheet(String path, int size) {
		_path = path;
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		load();
	}
	
	private void load() {
		try {
			URL a = SpriteSheet.class.getResource(_path);
			image = ImageIO.read(a);
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, _pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
