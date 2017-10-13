package plugin;

import java.util.ArrayList;
import java.util.List;

public class Entry {
	private String title;
	private List<String> recepies;
	private static final int maxLines = 14;
	private static final int maxChars = 20;

	public Entry(String title) {
		this.title = title;
		this.recepies = new ArrayList<String>();
	}

	public void addRecepie(String s) {
		recepies.add(s);
	}

	public String[] getPages() {

		int currentPage = 0;
		int linesFree = maxLines - lines(title);
		List<String> pages = new ArrayList<String>();
		pages.add(title);

		loop: for (String s : recepies) {
			while (true) {
				if (lines(s) <= linesFree) {
					linesFree = linesFree - lines(s);
					String before = "";
					try {
						before = pages.get(currentPage);
					} catch (IndexOutOfBoundsException e) {
						pages.add("");
					}
					pages.set(currentPage, before + s);
					continue loop;
				} else {
					currentPage++;
					linesFree = maxLines;
				}
			}
		}

		String[] result = new String[pages.size()];
		for (int i = 0; i < pages.size(); i++) {
			result[i] = pages.get(i);
		}

		return result;
	}

	private static int lines(String s) {
		return s.split("\n").length;
	}
}
