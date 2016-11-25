package com.jdhui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jdhui.db.afinal.FinalDb;
import com.jdhui.db.model.City;
import com.jdhui.db.model.Province;

import java.util.List;

public class DBManager {

    public static FinalDb getFinalDB(Context context) {
        return FinalDb.create(context, "jdhui", true, 4, new FinalDb.DbUpdateListener() {

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                if (db == null) {
                    return;
                }
                db.execSQL("DROP TABLE IF EXISTS tb_province "); //删除表:tb_province. 本1.0版本字段有变动
                db.execSQL("DROP TABLE IF EXISTS tb_city "); //删除表:tb_city. 本1.0版本字段有变动
            }
        });
    }

    /**
     * 保存所有省份
     *
     * @param context
     * @param list    省份集合
     */
    public static void saveProvinceList(Context context, List<Province> list) {
        FinalDb finalDb = getFinalDB(context);
//        finalDb.dropTable(Province.class);
//        if (list != null && list.size() > 0) {
//            try {
//                for (Province item : list) {
//                    finalDb.save(item);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        long beginTime = System.currentTimeMillis();
        SQLiteDatabase db = finalDb.getDb();
        db.beginTransaction();
        try {
            finalDb.deleteAll(Province.class);
            for (Province tagInfo : list) {
                finalDb.lhlSaveLHL(tagInfo);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            long endTime = System.currentTimeMillis();
            Log.i("aaa", (endTime - beginTime) + "");
        }
    }

    /**
     * 获取所有省份集合
     *
     * @param context
     * @return 所有省份集合
     */
    public static List<Province> getProvinceList(Context context) {
        FinalDb finalDb = getFinalDB(context);
        return finalDb.findAll(Province.class);
    }


    /**
     * 保存所有市
     *
     * @param context
     * @param list    市集合
     */
    public static void saveCityList(Context context, List<City> list) {
        FinalDb finalDb = getFinalDB(context);
//        finalDb.dropTable(City.class);
//        if (list != null && list.size() > 0) {
//            try {
//                for (City item : list) {
//                    finalDb.save(item);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        long beginTime = System.currentTimeMillis();
        SQLiteDatabase db = finalDb.getDb();
        db.beginTransaction();
        try {
            finalDb.deleteAll(City.class);
            for (City item : list) {
                finalDb.lhlSaveLHL(item);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            long endTime = System.currentTimeMillis();
            Log.i("aaa", (endTime - beginTime) + "");
        }
    }


    /**
     * 获取所有市集合
     *
     * @param context
     * @param pid     根据provinceId 即省份id来获取下辖市
     * @return 所有省份集合
     */
    public static List<City> getCityList(Context context, String pid) {
        FinalDb finalDb = getFinalDB(context);
        Log.i("aaa", pid);
        return finalDb.findAllByWhere(City.class, " pid = '" + pid + "'");
    }


    /**
     * 首页搜索，地图搜索，通勤找房搜索时都会根据城市id和type进行历史记录的存储
     * @param context
     * @param item
     *//*
    public static void saveSearchItem(Context context, SearchItem item) {
        FinalDb finalDb = getFinalDB(context);
        List<SearchItem> list = finalDb.findAllByWhere(SearchItem.class, " keyword = '" + item.getKeyword() + "' and cityId = ' " + item.getCityId() + "'");

        //防止重复保存
        if (list == null || list.size() <= 0) {
            finalDb.saveBindId(item);
        }else{//否则更新
            finalDb.update(item, " keyword = '" + item.getKeyword() + "' and cityId = '" + item.getCityId() + "'");
        }
    }

    *//**
     * 搜索界面搜索记录的函数
     *
     * @param cityId 城市id
     * @author 高瑞
     *//*
    public static List<SearchItem> getSearchItemByCity(Context context, long cityId) {
        FinalDb finalDb = getFinalDB(context);
        if (!finalDb.tableIsExist(TableInfo.get(SearchItem.class)))
            return new ArrayList<SearchItem>();
        return finalDb.findAllByWhere(SearchItem.class, " cityId=" + cityId, " time DESC ", " limit 0,10 ");
    }

    *//**
     * 目的地搜索删除历史记录
     *
     * @param context
     * @param cityId  城市id
     *//*
    public static void deleteSearchesByType(Context context, long cityId, long type) {
        FinalDb db = getFinalDB(context);
        String where = " cityId=" + cityId + " and "
                + " keywordType=" + type;
        db.deleteByWhere(SearchItem.class, where);
    }

    *//**
     * 从主界面点开的搜索页， 删除历史记录
     * keywordType 10表示目的地， 11表示通勤找房
     * @param context
     * @param cityId  城市id
     *//*
    public static void deleteSearchesExt(Context context, long cityId) {
        FinalDb db = getFinalDB(context);
        String where = " cityId=" + cityId + " and "
                + " keywordType<>10 and keywordType<>11 ";
        db.deleteByWhere(SearchItem.class, where);
    }

    *//****************************************************
     * 保存城市 的集合信息
     *
     * @param context
     * @return
     *//*
    public static boolean saveTabCityList(Context context, List<TabCity> cityList) {
        FinalDb finalDb = getFinalDB(context);
        finalDb.dropTable(TabCity.class);
        if (cityList != null && cityList.size() > 0) {
            try {
                for (TabCity item : cityList) {
                    LogUtils.e("saveTabCityList", item.getName());
                    finalDb.save(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    *//****************************************************
     * 保存新接口城市 的集合信息
     *
     * @param context
     * @return
     *//*
    public static boolean saveNewCityList(Context context, List<CityEntity> cityList) {
        FinalDb finalDb = getFinalDB(context);
        finalDb.dropTable(CityEntity.class);
        if (cityList != null && cityList.size() > 0) {
            try {
                for (CityEntity item : cityList) {
                    LogUtils.e("saveTabCityList", item.getName());
                    finalDb.save(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    public static List<TabCity> getTabCityList(Context context) {
        FinalDb finalDb = getFinalDB(context);
        return finalDb.findAll(TabCity.class);
    }

    public static List<CityEntity> getNewCityList(Context context) {
        FinalDb finalDb = getFinalDB(context);
        return finalDb.findAll(CityEntity.class);
    }

    public static List<CityEntity> getNewCityList(Context context, int mode) {
        FinalDb finalDb = getFinalDB(context);
        return finalDb.findAllByWhere(CityEntity.class, "mode='" + mode + "'");
    }

    public static CityEntity getNewCityEntity(Context context, int cityId) {
        FinalDb finalDb = getFinalDB(context);
        List<CityEntity> list = finalDb.findAllByWhere(CityEntity.class, "cityId='" + cityId + "'");
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    *//****************************************************
     * 保存区域 的集合信息
     *
     * @param context
     * @return
     *//*
    public static boolean saveTabRegionList(Context context, List<TabRegion> regionList, long cityId) {
        FinalDb finalDb = getFinalDB(context);
        // drop table before
        // finalDb.dropTable(TabRegion.class);
        SQLiteDatabase db = finalDb.getDb();
        db.beginTransaction();
        try {
            if (getRegionList(context).size() == 0) {
                finalDb.save(new TabRegion("不限", "0", cityId));
                if (regionList != null && regionList.size() > 0) {
                    for (TabRegion item : regionList) {
                        item.setCityId(cityId);
                        finalDb.save(item);
                    }
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return false;
    }

    public static List<TabRegion> getRegionList(Context context) {
        FinalDb finalDb = getFinalDB(context);
        return finalDb.findAllByWhere(TabRegion.class, "cityId='" + DDLoginSDK.initDDSDK(context).getCityId() + "'");
    }

    *//**
     * 保存商圈 的集合信息
     *
     * @param context
     * @return
     *//*
    public static boolean saveTabRegionZoneList(Context context, List<TabRegionZone> regionStationList, long cityId) {
        FinalDb finalDb = getFinalDB(context);
        // drop table before
        // finalDb.dropTable(TabRegionZone.class);
        // finalDb.saveBindId(new TabRegionZone("不限", regionStationList.get(0).getFatherIndex(), cityId));
        if (getRegionZoneListByWhere(context).size() == 0) {
            if (regionStationList != null && regionStationList.size() > 0) {
                for (TabRegionZone item : regionStationList) {
                    item.setCityId(cityId);
                    finalDb.saveBindId(item);
                }
            }
        }
        return false;
    }

    public static List<TabRegionZone> getRegionZoneListByWhere(Context context, String myId) {
        FinalDb finalDb = getFinalDB(context);
        if (myId == null || myId.equals(""))
            myId = "0";
        return finalDb.findAllByWhere(TabRegionZone.class, " fatherIndex='" + myId + "' and cityId='" + DDLoginSDK.initDDSDK(context).getCityId() + "'");
        // return finalDb.findAllByWhere(TabRegionZone.class, "fatherIndex="+myId, "districtCode");
    }

    public static List<TabRegionZone> getRegionZoneListByWhere(Context context) {
        FinalDb finalDb = getFinalDB(context);
        return finalDb.findAllByWhere(TabRegionZone.class, "cityId='" + DDLoginSDK.initDDSDK(context).getCityId() + "'");
        // return finalDb.findAllByWhere(TabRegionZone.class, "fatherIndex="+myId, "districtCode");
    }

    *//*******************************************************
     * 保存地铁线路 的集合信息
     *
     * @param context
     * @param subList
     * @return
     *//*
    public static boolean saveSubwayList(Context context, List<TabSubway> subList, long cityId) {
        FinalDb finalDb = getFinalDB(context);
        // drop table before
        SQLiteDatabase db = finalDb.getDb();
        db.beginTransaction();
        try {
            // finalDb.deleteByWhere(TabSubway.class, "cityId='" + cityId + "'");
            if (getSubwayList(context).size() == 0) {
                finalDb.save(new TabSubway("不限", "0", cityId));
                if (subList != null && subList.size() > 0) {
                    for (TabSubway item : subList) {
                        item.setCityId(cityId);
                        finalDb.save(item);
                    }
                }
            }
            LogUtils.e("saveSubwayList", "success");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return false;
    }

    *//**
     * 保存地铁站点 的集合信息
     *
     * @param context
     * @return
     *//*
    public static boolean saveSubwayStationList(Context context, List<TabSubwayStation> subListSta, long cityId) {
        FinalDb finalDb = getFinalDB(context);
        // drop table before
        // finalDb.dropTable(TabSubwayStation.class);
        // finalDb.saveBindId(new TabSubwayStation("不限", subListSta.get(0).getFatherIndex(), cityId));
        if (getSubwayStationListByWhere(context).size() == 0)
            if (subListSta != null && subListSta.size() > 0) {
                for (TabSubwayStation item : subListSta) {
                    item.setCityId(cityId);
                    finalDb.saveBindId(item);
                }
            }
        return false;
    }

    public static boolean saveSubwayStation(Context context, TabSubwayStation item) {
        FinalDb finalDb = getFinalDB(context);
        finalDb.saveBindId(item);
        return false;
    }

    public static List<TabSubway> getSubwayList(Context context) {
        FinalDb finalDb = getFinalDB(context);
        return finalDb.findAllByWhere(TabSubway.class, "cityId='" + DDLoginSDK.initDDSDK(context).getCityId() + "'");
    }

    public static List<TabSubwayStation> getSubwayStationListByWhere(Context context, String myId) {
        FinalDb finalDb = getFinalDB(context);
        if (myId == null || myId.equals(""))
            myId = "0";
        return finalDb.findAllByWhere(TabSubwayStation.class, "fatherIndex='" + myId + "' and cityId='" + DDLoginSDK.initDDSDK(context).getCityId() + "'");
        // return finalDb.findAllByWhere(TabRegionZone.class, "fatherIndex="+myId, "districtCode");
    }

    public static List<TabSubwayStation> getSubwayStationListByWhere(Context context) {
        FinalDb finalDb = getFinalDB(context);
        return finalDb.findAllByWhere(TabSubwayStation.class, "cityId='" + DDLoginSDK.initDDSDK(context).getCityId() + "'");
        // return finalDb.findAllByWhere(TabRegionZone.class, "fatherIndex="+myId, "districtCode");
    }

    *//*********************************************************
     * 预约看房相关
     *
     * @param context
     * @return
     *//*

    public static boolean saveLookIds(Context context, Province li) {
        FinalDb finalDb = getFinalDB(context);
        List<Province> list = finalDb.findAllByWhere(Province.class, " lookid = '" + li.getLookid() + "' and  phone = '" + li.getPhone() + "' and cityId = '" + DDLoginSDK.initDDSDK(context).getCityId() + "'");
        if (list != null && list.size() <= 0) {
            finalDb.saveBindId(li);
            return true;
        }
        return false;
    }

    // public static void saveAllLookIds(Context context, String userPhone, List<Integer> priductIds) {
    // FinalDb finalDb = getFinalDB(context);
    // //现将所有本地的该用户的房源信息删除 替换成服务器的
    // // String where = " phone ='" + userPhone+"'" ;
    // // finalDb.deleteByWhere(Province.class, where);
    //
    // for (Integer id : priductIds) {
    // finalDb.updateBindId(new Province(userPhone,id+""));
    // }
    // }
    public static void saveAllLookIds(Context context, String userPhone, List<Province> lookIds) {
        FinalDb finalDb = getFinalDB(context);
        // 现将所有本地的该用户的房源信息删除 替换成服务器的
        String where = " phone ='" + userPhone + "' and cityId = '" + DDLoginSDK.initDDSDK(context).getCityId() + "'";
        finalDb.deleteByWhere(Province.class, where);

        for (Province lookId : lookIds) {
            finalDb.saveBindId(new Province(userPhone, lookId.getLookid() + "", lookId.getHouseState(), (long) DDLoginSDK.initDDSDK(context).getCityId()));
        }
    }

    public static List<Province> getAllLookIdsByUser(Context context, String phone) {
        FinalDb db = getFinalDB(context);
        return db.findAllByWhere(Province.class, "phone = '" + phone + "'" + " and cityId = '" + DDLoginSDK.initDDSDK(context).getCityId() + "'");
        // return db.findAllByWhere(Province.class, "phone = '"+phone+"'"+" and houseState = '1'");
    }

    public static int getLookIdsSizeByUser(Context context, String phone) {
        try {
            FinalDb db = getFinalDB(context);
            // List<Province> list = db.findAllByWhere(Province.class, "phone = '"+phone+"'");
            List<Province> list = db.findAllByWhere(Province.class, "phone = '" + phone + "'" + " and houseState = '2'" + " and cityId = '" + DDLoginSDK.initDDSDK(context).getCityId() + "'");
            if (list != null) {
                return list.size();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static void deleteLookIdByUser(Context context, String phone, String itemId) {
        if (itemId == null || "".equals(itemId.trim())) {
            return;
        }
        FinalDb finalDb = getFinalDB(context);
        String where = " lookid ='" + itemId + "' and  phone = '" + phone + "'" + " and cityId = '" + DDLoginSDK.initDDSDK(context).getCityId() + "'";
        finalDb.deleteByWhere(Province.class, where);
    }

    public static void deleteSubmitedLookIdsByUser(Context context, String phone, String ids) {
        if (phone == null || "".equals(phone.trim()) || ids == null || "".equals(ids.trim())) {
            return;
        }
        FinalDb finalDb = getFinalDB(context);
        String[] idsArr = ids.split(",");
        for (String lookid : idsArr) {
            String where = " phone ='" + phone + "' and  lookid = '" + lookid + "'" + " and cityId = '" + DDLoginSDK.initDDSDK(context).getCityId() + "'";
            finalDb.deleteByWhere(Province.class, where);
        }
    }

    public static boolean isLookIdHas(Context context, String phone, String id) {
        FinalDb db = getFinalDB(context);
        List<Province> list = db.findAllByWhere(Province.class, " lookid='" + id + "' and  phone = '" + phone + "'" + " and cityId = '" + DDLoginSDK.initDDSDK(context).getCityId() + "'");
        if (list != null) {
            return list.size() > 0;
        }
        return false;
    }

    public static boolean checkInvailedLookids(Context context, User user, List<Rent> rentList) {
        FinalDb db = getFinalDB(context);
        List<Province> list = db.findAllByWhere(Province.class, " phone = '" + user.getMobile() + "'" + " and cityId = '" + DDLoginSDK.initDDSDK(context).getCityId() + "'");
        boolean isHasInvailedLookid = false;// 有无下架的房源id
        if (list != null) {
            List<Province> dels = new ArrayList<Province>();
            for (Province item : list) {
                String id = item.getLookid();
                for (Rent rent : rentList) {
                    if (id.equals(rent.getId() + "")) {
                        dels.add(item);
                        break;
                    }
                }
            }
            list.removeAll(dels);
            if (list.size() == 0)// 房源全部在架
                return false;
            for (Province id : list) {
                String where = " phone ='" + user.getMobile() + "' and  lookid = '" + id.getLookid() + "'" + " and cityId = '" + DDLoginSDK.initDDSDK(context).getCityId() + "'";
                db.deleteByWhere(Province.class, where);
                isHasInvailedLookid = true;
            }
            // 将无效（已下架） 的id从数据库里删除
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).getLookid() + ",");
            }
            String str = sb.toString();
            Build4SearchHouse build = new Build4SearchHouse();
            DataService.deleteShopping(user.getId(), str.substring(0, str.length() - 1), build.getMap());
        }
        return isHasInvailedLookid;
    }

    *//*********************************************************
     * 保存小区信息
     *
     * @param context
     * @param cityId
     * @return
     *//*

    public static boolean savePlotInfoList(Context context, List<PlotInfo> list, long cityId) {
        FinalDb finalDb = getFinalDB(context);
        SQLiteDatabase db = finalDb.getDb();
        db.beginTransaction();

        try {
            // finalDb.dropTable(PlotInfo.class);
            LogUtils.e("savePlotInfoList", "start: " + System.currentTimeMillis() + "");
            finalDb.checkTableExist(PlotInfo.class);
            for (PlotInfo item : list) {
                item.setCityId(cityId);
                finalDb.lhlSaveLHL(item);
            }
            db.setTransactionSuccessful();
            UserUtil.getInstance().setHasKeyWord(context, cityId + "", true);
            LogUtils.e("savePlotInfoList", "success: " + System.currentTimeMillis() + "");

        } catch (Exception e) {
            LogUtils.e("savePlotInfoList", "error: " + System.currentTimeMillis() + "");
            e.printStackTrace();
            return false;
        } finally {
            LogUtils.e("savePlotInfoList", "end: " + System.currentTimeMillis() + "");
            db.endTransaction();
        }
        return true;
    }

    *//*********************************************************
     * 删除小区信息
     *
     * @param context
     * @return
     *//*

    public static boolean deletePlotInfoList(Context context) {
        FinalDb finalDb = getFinalDB(context);
        synchronized (finalDb) {
            finalDb.dropTable(PlotInfo.class);
        }

        return true;
    }

    *//*********************************************************
     * 保存jpush信息
     *
     * @param context
     * @return
     *//*

    public static void saveJpush(Context context, JPush jp) {
        FinalDb finalDb = getFinalDB(context);
        finalDb.saveBindId(jp);
    }

    public static int getAllDaiBanSizeByUser(Context context, String phone) {
        int totalNum = 0;
        FinalDb db = getFinalDB(context);
        List<Province> listLookids = db.findAllByWhere(Province.class, "phone = '" + phone + "'" + " and houseState = '2'" + " and cityId = '" + DDLoginSDK.initDDSDK(context).getCityId() + "'");
        if (listLookids != null) {
            totalNum = listLookids.size();
        }
        List<JPush> listJPush = db.findAllByWhere(JPush.class, "phone = '" + phone + "'");
        if (listJPush != null) {
            totalNum = totalNum + listJPush.size();
        }
        return totalNum;
    }

    public static int getJpushSizeByUserAndType(Context context, String phone, String type) {
        FinalDb db = getFinalDB(context);
        List<JPush> list = db.findAllByWhere(JPush.class, "phone = '" + phone + "' and  type = '" + type + "'");
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static int getJpushSizeAll(Context context, String phone) {
        FinalDb db = getFinalDB(context);
        List<JPush> list = db.findAllByWhere(JPush.class, "phone = '" + phone + "'");
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static void delJpushByUserAndType(Context context, String phone, String type) {
        FinalDb db = getFinalDB(context);
        db.deleteByWhere(JPush.class, "phone = '" + phone + "' and  type = '" + type + "'");
    }

    *//*********************************************************
     * 保存继续搜索
     *
     * @param context
     * @return
     *//*

    public static boolean saveContinueSearch(Context context, ContinueSearch item) {
        FinalDb finalDb = getFinalDB(context);
        //只留一条记录
        finalDb.deleteAll(ContinueSearch.class);
        return finalDb.saveBindId(item);
    }

    public static void deleteContinueSearch(Context context) {
        FinalDb finalDb = getFinalDB(context);
        finalDb.deleteAll(ContinueSearch.class);
    }

    public static List<ContinueSearch> getCSList(Context context, int cityId) {
        FinalDb finalDb = getFinalDB(context);
        return finalDb.findAllByWhere(ContinueSearch.class, " cityId=" + cityId);
    }

    public static void updateCS(Context context, String key, String value) {
        FinalDb finalDb = getFinalDB(context);
        List<ContinueSearch> list = finalDb.findAll(ContinueSearch.class);
        if (list == null || list.size() <= 0)
            return;
        TableInfo table = TableInfo.get(ContinueSearch.class);
        StringBuilder sb = new StringBuilder(" update " + table.getTableName() + " set " + key + " = '" + value + "'");
        SqlInfo sqlInfo = new SqlInfo();
        sqlInfo.setSql(sb.toString());
        sqlInfo.setBindArgs(new LinkedList<Object>());
        finalDb.exeSqlInfo(sqlInfo);

    }


    *//*********************************************************
     * 保存消息
     *
     * @param context
     * @return
     *//*
    public static boolean saveNotification(Context context, Notification noti) {
        FinalDb finalDb = getFinalDB(context);
        finalDb.saveBindId(noti);
        return false;
    }

    public static boolean deleteNotification(Context context, List<Notification> notis) {
        if (notis == null || notis.size() == 0) {
            return false;
        }
        boolean result = true;
        try {
            FinalDb finalDb = getFinalDB(context);
            StringBuilder where = new StringBuilder(" _id in (");
            for (Notification noti : notis) {
                where.append(noti.get_id()).append(",");
            }
            where.deleteCharAt(where.length() - 1);
            where.append(") ");

            finalDb.deleteByWhere(Notification.class, where.toString());
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    *//**
     * 保存所有标签列表
     *
     * @param context
     * @param tagInfoList
     *//*
    public static void saveAllTagList(Context context, List<TagInfo> tagInfoList) {
        FinalDb finalDb = getFinalDB(context);
        SQLiteDatabase db = finalDb.getDb();
        db.beginTransaction();
        // 现将所有本地的该用户的房源信息删除 替换成服务器的
        try {
            finalDb.deleteAll(TagInfo.class);
            for (TagInfo tagInfo : tagInfoList) {
                finalDb.lhlSaveLHL(tagInfo);
            }
            db.setTransactionSuccessful();

            SharedPreferenceManager.getInstance(context).setHasTagsTime(System.currentTimeMillis());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


    public static String getTagByIdandType(Context context, long id, int type) {
        try {
            FinalDb db = getFinalDB(context);
            // List<Province> list = db.findAllByWhere(Province.class, "phone = '"+phone+"'");
            List<TagInfo> list = db.findAllByWhere(TagInfo.class, "id = '" + id + "'" + " and type = '" + type + "'");
            if (list != null && list.size() > 0) {
                return list.get(0).getName();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static List<TagInfo> getTagListByType(Context context, int type) {
        List<TagInfo> list = new ArrayList<>();
        try {
            FinalDb db = getFinalDB(context);
            // List<Province> list = db.findAllByWhere(Province.class, "phone = '"+phone+"'");
            list = db.findAllByWhere(TagInfo.class, " type = '" + type + "'");
            if (list != null && list.size() > 0) {
                return list;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }


    public static List<String> getTagListSortByTypeAndList(Context context, int type, List<Integer> list) {
        List<String> sList = new ArrayList<>();
        try {
            FinalDb db = getFinalDB(context);
            // List<Province> list = db.findAllByWhere(Province.class, "phone = '"+phone+"'");
            List<TagInfo> mList = db.findAllByWhere(TagInfo.class, " type = '" + type + "'" + " order by weight");
            if (mList != null && mList.size() > 0) {
                for (TagInfo tagInfo : mList) {
                    if (list.contains(Integer.parseInt(tagInfo.getId() + ""))) {
                        sList.add(tagInfo.getName());
                    }
                }
                return sList;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sList;
    }

    *//**
     * 保存所有评价标签列表
     *
     * @param context
     *//*
    public static void saveAllEvaluateList(Context context, List<EvaluateType> list) {
        FinalDb finalDb = getFinalDB(context);
        SQLiteDatabase db = finalDb.getDb();
        db.beginTransaction();
        // 现将所有本地的该用户的房源信息删除 替换成服务器的
        try {
            finalDb.deleteAll(EvaluateType.class);
            for (EvaluateType evaluateType : list) {
                finalDb.lhlSaveLHL(evaluateType);
            }
//            if (map.containsKey("complete")) {
//                Map<String, Object> completeMap = (Map<String, Object>) map.get("complete");
//                saveMap(completeMap, 1, finalDb);
//            }
//            if (map.containsKey("cancel")) {
//                Map<String, Object> cancelMap = (Map<String, Object>) map.get("cancel");
//                saveMap(cancelMap, 1, finalDb);
//            }
            db.setTransactionSuccessful();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    private static void saveMap(Map<String, Object> map, int type, FinalDb finalDb) {
        for (String key : map.keySet()) {
            List<EvaluateType> evaluateTypeList = JSON.parseArray(map.get(key).toString(), EvaluateType.class);
            if (evaluateTypeList != null)
                for (EvaluateType evaluateType : evaluateTypeList) {
                    evaluateType.setScore(Integer.parseInt(key));
                    finalDb.lhlSaveLHL(evaluateType);
                }
        }
    }

    public static List<EvaluateType> getEvaluateTypeListByStar(Context context, int score, int state) {
        List<EvaluateType> list = new ArrayList<>();
        try {
            FinalDb db = getFinalDB(context);
            list = db.findAllByWhere(EvaluateType.class, " score = '" + score + "' and " + "userOrderStatus = '" + state + "'");
            if (list != null && list.size() > 0) {
                return list;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String getEvaluateContentById(Context context, int id) {
        try {
            FinalDb db = getFinalDB(context);
            List<EvaluateType> list = db.findAllByWhere(EvaluateType.class, " id = '" + id + "'");
            if (list != null && list.size() > 0) {
                return list.get(0).getContent();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }*/

}
