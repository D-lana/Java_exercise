import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.nio.file.Files;
import java.util.Set;

public class Program {

	private static Map<String, String> myMap = new HashMap<>();

	public static void getSignatures() throws IOException {
		File		fileSig = new File("signatures.txt");
		Scanner		signatures = new Scanner(fileSig);
		String		line;
		String[]	tmp;

		for (int i = 0; signatures.hasNextLine(); i++) {
			line = signatures.nextLine();
			tmp = line.split(", ");
			myMap.put(tmp[1], tmp[0]);
		}
	}

	public static void main(String[] args) throws IOException {
		int			flag = 0;

		getSignatures();

		File		fileResult = new File("result.txt");
		PrintWriter	pw = new PrintWriter(fileResult);
		String		path;

		while (true) {
			Scanner	scan = new Scanner(System.in);
			path = scan.nextLine();
			if (path.equals("42")) {
				break ;
			}
			StringBuilder	strb = new StringBuilder();
			String			str;

			try {
				FileInputStream shifr = new FileInputStream(path);
				Byte[] b = new Byte[32];
				byte[] a = new byte[32];
				Integer res;
				shifr.read(a);

				for (int i = 0; i < 32; i++) {
					b[i] = Byte.valueOf(a[i]);
					res = b[i].intValue();
					str = Integer.toHexString(res);
					strb.append(str + " ");
				}
				str = strb.toString();
				str = str.toUpperCase();
				System.out.println(str); ///////////////
				String		tmps;
				String		format;
				Set<String>	res1 = myMap.keySet();
				String[]	strings = new String[res1.size()];
				strings = res1.toArray(strings);

				for (int i = 0; i < res1.size(); i++) {
					int len = strings[i].length();
					tmps = str.substring(0, len);
					if(tmps.equals(strings[i])) {
						format = myMap.get(strings[i]);
						pw.println(format);
						System.out.println("PROCESSED");
						flag = 1;
					}
				}
				if (flag == 0) {
					throw new FileNotFoundException();
				}
				flag = 0;
			} catch (FileNotFoundException e) {
				System.out.println("UNDEFINED");
			}

		}
		pw.close();
	}
}
