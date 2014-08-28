package test;

public class Test {

	/**
	 * ˮ�ɻ�����ָһ��Nλ��������N>=3��������ÿ��λ�ϵ����ֵ�N����֮�͵����������� �磺153 = 1�����η� + 5�����η� + 3�����η���
	 * ����Ҫ���д����,��������Nλˮ�ɻ�����
	 * 
	 * �����ʽ�� ������һ���и���һ��������N��3<=N<=7����
	 * 
	 * �����ʽ�� ������˳���������Nλˮ�ɻ�����ÿ������ռһ�С�
	 * 
	 * ���������� 3
	 * 
	 * ��������� 153 370 371 407
	 * 
	 * @author kane.sun
	 * 
	 */

	public static void main(String... args) {
		Test test = new Test();
		test.printShuiXian(3);
	}

	public void printShuiXian(int range) {
		int start = (int) Math.pow(10, range - 1);
		int end = (int) Math.pow(10, range);

		for (int i = start; i < end; i++) {
			if (isAShuiXian(i)) {
				System.out.println(i);
			}
		}
	}

	private boolean isAShuiXian(int number) {
		int targetNumber = 0;
		char[] numberArray = String.valueOf(number).toCharArray();
		int range = numberArray.length;
		for (int i = 0; i < range; i++) {
			targetNumber += (int) Math.pow(Integer.parseInt(numberArray[i] + ""), range);
		}

		return targetNumber == number;
	}
}