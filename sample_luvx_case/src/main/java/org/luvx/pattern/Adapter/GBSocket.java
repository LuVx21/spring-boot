package org.luvx.pattern.Adapter;

/**
 * 符合中国标准的插座(三口扁行插座)
 */
public class GBSocket implements GBSocketInterface{
	
	@Override
	public void powerWithThreeFlat() {
		System.out.println("使用三项扁头插孔供电");
	}
}