package uet.oop.bomberman.graphics;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất
 * Class này giúp lấy ra các sprite riêng từ 1 ảnh chung duy nhất đó
 */
public class SpriteSheet {

	List<Sprite> spriteList = new ArrayList<>();
	private String _path;

	public final int SIZE_X;

	public final int SIZE_Y;
	public int[] _pixels;
	public BufferedImage image;

	public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256);
	public static SpriteSheet evil = new SpriteSheet("/textures/classic2.png", 256);
	public static SpriteSheet gigaTiles = new SpriteSheet("/textures/arnold.png", 16);
	public static SpriteSheet creeper1 = new SpriteSheet("/sprites/creeper1.png", 16);
	public static SpriteSheet creeper2 = new SpriteSheet("/sprites/creeper2.png", 16);
	public static SpriteSheet creeper3 = new SpriteSheet("/sprites/creeper3.png", 16);

	public static SpriteSheet gTiles = new SpriteSheet("/textures/player_gold.png", 64);

	public static SpriteSheet gunTiles = new SpriteSheet("/textures/full_auto.png", 3072, 48);
	public static SpriteSheet gunFxTiles = new SpriteSheet("/textures/fx_full_auto.png", 3072, 48);

	public static SpriteSheet gunFlashTiles = new SpriteSheet("/textures/flash_full_auto.png", 3072, 48);

	public static Image middleEastTiles = new Image("/textures/mid_man.png",32 * 4, 32 * 3, true, true);

	public SpriteSheet(String path, int size) {
		_path = path;
		SIZE_X = size;
		SIZE_Y = size;
		_pixels = new int[SIZE_X * SIZE_Y];
		load();
	}

	public SpriteSheet(String path, int sizeX, int sizeY) {
		_path = path;
		SIZE_X = sizeX;
		SIZE_Y = sizeY;
		_pixels = new int[SIZE_X * SIZE_Y];
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