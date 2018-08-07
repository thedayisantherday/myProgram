package com.example.zxg.myprogram.widget.wheelview.helper;

import android.content.Context;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.widget.wheelview.model.CityModel;
import com.example.zxg.myprogram.widget.wheelview.model.DistrictModel;
import com.example.zxg.myprogram.widget.wheelview.model.ProvinceModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonParserHandle {
	private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();
	
	private ProvinceModel mProvinceModel;
	private CityModel mCityModel;
	private DistrictModel mDistrictModel;
	
	public List<ProvinceModel> getProvinceList(){
		return provinceList;
	}
	
	public JsonParserHandle(Context context){
		parserJson(context);
	}

	/**
	 * 城市列表json解析
	 * @param context
	 */
	private void parserJson(Context context){
		try{
			InputStream inputStream = context.getResources().openRawResource(R.raw.chinese_regions);
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader buffered = new BufferedReader(reader);
			StringBuffer response = new StringBuffer();
			String temp;
			if((temp = buffered.readLine()) != null){
				response.append(temp);
				response.append("\n");
			}
			buffered.close();
			reader.close();
			inputStream.close();
			
			JSONArray provinceJsonArray = new JSONArray(response.toString());
			for(int i=0; i<provinceJsonArray.length(); i++){
				JSONObject jsonObject = (JSONObject) provinceJsonArray.opt(i);
				mProvinceModel = new ProvinceModel();
				mProvinceModel.setName(jsonObject.getString("name"));
				mProvinceModel.setCityList(new ArrayList<CityModel>());
				JSONArray cityJsonArray = (JSONArray)jsonObject.get("subCityArray");
				for(int j=0; j<cityJsonArray.length(); j++){
					JSONObject cityJsonObject = (JSONObject) cityJsonArray.opt(j);
					mCityModel = new CityModel();
					mCityModel.setName(cityJsonObject.getString("name"));
					mCityModel.setDistrictList(new ArrayList<DistrictModel>());
					JSONArray districtJsonArray = (JSONArray)cityJsonObject.get("subDistrictArray");
					for(int k=0; k<districtJsonArray.length(); k++){
						JSONObject districtJsonObject = (JSONObject) districtJsonArray.opt(k);
						mDistrictModel = new DistrictModel();
						mDistrictModel.setName(districtJsonObject.getString("name"));
						mDistrictModel.setZipcode(districtJsonObject.getString("zip"));
						mCityModel.getDistrictList().add(mDistrictModel);
					}
					mProvinceModel.getCityList().add(mCityModel);
				}
				provinceList.add(mProvinceModel);
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
}
