package com.wangge.app.server.monthTask.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wangge.app.server.entity.Region;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.monthTask.entity.MonthTask;
import com.wangge.app.server.monthTask.entity.MonthTaskExecution;
import com.wangge.app.server.monthTask.entity.MonthTaskSub;
import com.wangge.app.server.monthTask.entity.MonthshopBasData;
import com.wangge.app.server.monthTask.repository.MonthTaskExecutionRepository;
import com.wangge.app.server.monthTask.repository.MonthTaskRepository;
import com.wangge.app.server.monthTask.repository.MonthTaskSubRepository;
import com.wangge.app.server.monthTask.repository.MonthshopBasDataRepository;
import com.wangge.app.server.repository.RegionRepository;
import com.wangge.app.server.repository.RegistDataRepository;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.app.server.util.DateUtil;

@Service
public class MonthTaskServiceImpl implements MonthTaskServive {
	@Autowired
	MonthTaskRepository monthTaskrep;
	@Autowired
	RegionRepository regionRep;
	@Autowired
	MonthshopBasDataRepository monthShopDRep;
	@Autowired
	MonthTaskSubRepository subTaskRep;
	@Autowired
	MonthTaskExecutionRepository mtExecRepository;
	@Autowired
	RegistDataRepository registRep;
	@Autowired
	SalesmanRepository salRep;
	@Autowired
	MonthTaskSchedule monthSche;

	private Integer[] levels = new Integer[] { 20, 15, 10, 7, 4 };

	@Override
	public ResponseEntity<Map<String, Object>> findTask(String userid) {
		String month = DateUtil.getPreMonth(new Date(), 1);
		MonthTask monthTask = monthTaskrep.findFirstByMonth(month, userid);

		Map<String, Object> taskmap = new HashMap<String, Object>();
		if (null == monthTask) {
			return generateErrorResp(taskmap, "1");
		}
		/*
		 * "code": 0, "msg": "", “regionId”: ”37001”, //业务所属id,为任务分配准备
		 * ‘maintaskid’:””,
		 */
		try {
			taskmap.put("code", 0);
			taskmap.put("msg", "");
			taskmap.put("regionId", monthTask.getRegionid());
			taskmap.put("maintaskId", monthTask.getId());

			Class<? extends MonthTask> mclass = monthTask.getClass();
			List<Map<String, Object>> dList = new ArrayList<Map<String, Object>>();
			for (Integer i : levels) {
				Map<String, Object> datamap = new HashMap<String, Object>();
				if (i < 10) {
					datamap.put("level", "0" + i);
				} else {
					datamap.put("level", i + "");
				}
				datamap.put("goal", getReflectInt(mclass.getDeclaredMethod("getTal" + i + "goal").invoke(monthTask)));
				datamap.put("set", getReflectInt(mclass.getDeclaredMethod("getTal" + i + "set").invoke(monthTask)));
				dList.add(datamap);
			}
			taskmap.put("obj", dList);
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
			e.printStackTrace();
			return generateErrorResp(taskmap, "0");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return generateErrorResp(taskmap, "0");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return generateErrorResp(taskmap, "0");
		}
		return new ResponseEntity<Map<String, Object>>(taskmap, HttpStatus.OK);
	}

	/**
	 * @param o
	 * @return
	 */
	private int getReflectInt(Object o) {
		return o == null ? 0 : Integer.parseInt(o + "");
	}

	/**
	 * 返回错误的数据类型
	 * 
	 * @param taskmap
	 * @param flag
	 *            "0" 程序错误 ,"1",没有数据
	 * @return
	 */
	private ResponseEntity<Map<String, Object>> generateErrorResp(Map<String, Object> taskmap, String flag) {
		taskmap.clear();
		taskmap.put("code", flag);
		return new ResponseEntity<Map<String, Object>>(taskmap, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Map<String, Object>> findRegion(String regionId) {
		Map<String, Object> taskmap = new HashMap<String, Object>();
		try {
			List<Region> rlist = regionRep.findByParentId(regionId);
			if (null == rlist || rlist.size() < 1) {
				return generateErrorResp(taskmap, "1");

			}
			List<Map<String, String>> dList = new ArrayList<Map<String, String>>();
			Map<String, String> datamap1 = new HashMap<String, String>();
			datamap1.put("id", regionId);
			datamap1.put("name", "所有区域");
			dList.add(datamap1);
			getMappedSubRegion(rlist, dList);

			taskmap.put("code", 0);
			taskmap.put("msg", "");
			taskmap.put("region", dList);
		} catch (Exception e) {
			e.printStackTrace();
			return generateErrorResp(taskmap, "0");
		}
		return new ResponseEntity<Map<String, Object>>(taskmap, HttpStatus.OK);
	}

	/**
	 * 得到一个区域的下级所有区域的名称到集合中
	 * 
	 * @param rlist
	 *            下级区域列表
	 * @param dList
	 *            要保存的集合
	 */
	private void getMappedSubRegion(Collection<Region> rlist, List<Map<String, String>> dList) {
		for (Region r : rlist) {
			Map<String, String> datamap = new HashMap<String, String>();
			datamap.put("id", r.getId());
			String name = r.getName().replace("\n", "");
			datamap.put("name", name);
			dList.add(datamap);
			Collection<Region> sublist = r.getChildren();
			if (null != sublist && sublist.size() > 0) {
				getMappedSubRegion(sublist, dList);
			}
		}
	}

	/**
	 * query.where(cb.like(namePath, "%李%"), cb.like(nicknamePath, "%王%"));
	 *
	 **/
	@Override
	public ResponseEntity<Map<String, Object>> findShopBy(Map<String, Object> params, Pageable page) {
		String goalstr = params.get("EQ_goal") == null ? "" : params.get("EQ_goal") + "";
		int goal = !"".equals(goalstr) ? Integer.parseInt(goalstr) : 0;
		params.remove("EQ_goal");
		String regionId = params.get("LK_regionId").toString();
		params.remove("LK_regionId");
		Set<String> regionSet = getSubShopRegion(regionId);
		Page<MonthshopBasData> data = monthShopDRep.findAll(new Specification<MonthshopBasData>() {
			@Override
			public Predicate toPredicate(Root<MonthshopBasData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				// root = query.from(MonthshopBasData.class);
				List<Predicate> predicates = new ArrayList<Predicate>();
				String month = DateUtil.getPreMonth(new Date(), 1);
				// 月份为默认查询条件,为下个月

				if (null == params.get("EQ_month")) {
					predicates.add(cb.equal(root.get("month"), month));

				}
				Join<MonthshopBasData, MonthTaskSub> leftJoin = root
						.join(root.getModel().getSingularAttribute("monthTaskSub", MonthTaskSub.class), JoinType.LEFT);
				predicates.add(cb.or(cb.equal(leftJoin.get("goal"), goal), cb.equal(root.get("used"), 0)));
				predicates.add(root.get("regionId").in(regionSet));
				createPedicateByMap(params, root, cb, predicates);

				return cb.and(predicates.toArray(new Predicate[] {}));
			}

		}, page);
		int tal = data.getTotalPages();
		Map<String, Object> dmap = new HashMap<String, Object>();
		List<MonthshopBasData> dlist = data.getContent();
		/*
		 * 返回数据格式 {“regionId”:”370281”, ”lastmonthcount”:”2” ,”monthAvg”:1,”
		 * month”:”2016-06”, ”visitCount”:0,”userd”:0, ”shopName”:”章丘魅族手机专卖”},
		 * 
		 */
		List<Map<String, Object>> ulist = new ArrayList<Map<String, Object>>();
		for (MonthshopBasData sd : dlist) {
			Map<String, Object> Obj = new HashMap<String, Object>();
			Obj.put("regionId", sd.getRegionId());
			Obj.put("lastmonthcount", sd.getLastmonthcount() + "");
			Obj.put("monthAvg", sd.getMonthAvg() + "");
			Obj.put("month", sd.getMonth());
			Obj.put("visitCount", sd.getVisitCount() + "");
			Obj.put("used", sd.getUsed());
			Obj.put("shopName", sd.getRegistData().getShopName());
			Obj.put("shopId", sd.getRegistData().getId());
			Obj.put("monthsdId", sd.getId());
			ulist.add(Obj);
		}
		dmap.put("obj", ulist);
		dmap.put("totalPages", tal);
		dmap.put("msg", "");
		return new ResponseEntity<Map<String, Object>>(dmap, HttpStatus.OK);
	}

	/**
	 * 通过区域id,得到其下所有的区域id;
	 * 
	 * @param regionId
	 * @return
	 */
	private Set<String> getSubShopRegion(String regionId) {
		Set<String> regionSet = new HashSet<String>();
		regionSet.add(regionId);
		List<Region> rlist = regionRep.findByParentId(regionId);
		for (Region r : rlist) {
			regionSet.add(r.getId());
			getSubRegionId(regionSet, r);
		}

		return regionSet;
	}

	/**
	 * 循环迭代取出单个区域下的所有区域
	 * 
	 * @param regionSet
	 *            要保存的集合
	 * @param r
	 *            区域
	 */
	private void getSubRegionId(Set<String> regionSet, Region r) {
		Collection<Region> childC = r.getChildren();
		if (childC != null && childC.size() > 0) {
			for (Region r1 : childC) {
				regionSet.add(r1.getId());
				getSubRegionId(regionSet, r1);
			}
		}
		return;
	}

	/**
	 * predicate的通用处理方法
	 * 
	 * @param params
	 * @param root
	 * @param cb
	 * @param predicates
	 * @throws NumberFormatException
	 */
	private void createPedicateByMap(Map<String, Object> params, Root<?> root, CriteriaBuilder cb,
			List<Predicate> predicates) throws NumberFormatException {
		for (Map.Entry<String, Object> search : params.entrySet()) {
			String keyAndFilter = search.getKey();
			Object value = search.getValue();
			String key = keyAndFilter.split("_")[1];
			String filter = keyAndFilter.split("_")[0];
			switch (filter) {
			case "EQ":
				predicates.add(cb.equal(root.get(key), value));
				break;
			case "LK":
				predicates.add(cb.like(root.get(key), "%" + value + "%"));
				break;
			case "GT":
				predicates.add(cb.greaterThan(root.get(key), Integer.parseInt(value + "")));
				break;
			case "LT":
				predicates.add(cb.lessThan(root.get(key), Integer.parseInt(value + "")));
				break;
			}
		}
	}

	@Override
	public void save(Map<String, Object> talMap)
			throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException {
		/*
		 * "monthTaskId": 6, "goal": 10 , “monthsdId": --没有就不传 [ 49 , 50 ]
		 * ,保存需关联的店铺历史fk “CancelId": --没有就不传 [49] 取消需关联的店铺历史fk
		 */
		Integer mainTaskId = (int) talMap.get("monthTaskId");
		MonthTask monthTask = monthTaskrep.getOne((long) mainTaskId);
		Class<? extends MonthTask> mclass = monthTask.getClass();
		Integer goal = (Integer) talMap.get("goal");
		Integer set = Integer.parseInt(mclass.getDeclaredMethod("getTal" + goal + "set").invoke(monthTask) + "");
		@SuppressWarnings("unchecked")
		// 新增外键集合
		List<String> monthsdIdList = null == talMap.get("monthsdId") ? new ArrayList<String>()
				: (List<String>) talMap.get("monthsdId");
		@SuppressWarnings("unchecked")
		// 取消外键集合
		List<String> CancelIdList = null == talMap.get("cancelId") ? new ArrayList<String>()
				: (List<String>) talMap.get("cancelId");

		List<MonthTaskSub> oldTList = new ArrayList<MonthTaskSub>();
		List<MonthshopBasData> sTList = new ArrayList<MonthshopBasData>();
		for (String id : monthsdIdList) {
			if ("".equals(id))
				continue;
			MonthshopBasData monShopd = monthShopDRep.findOne(Long.parseLong(id));
			MonthTaskSub ms = new MonthTaskSub();
			ms.setGoal(goal);
			monShopd.setUsed(1);
			monShopd.setMonthTaskSub(null);
			MonthTask monthT = monthTaskrep.findOne((long) mainTaskId);
			ms.setMonthsd(monShopd);
			ms.setMonthTask(monthT);
			MonthTaskSub ms1 = subTaskRep.save(ms);
			monShopd.setMonthTaskSub(ms1);
			set++;
		}
		for (String id : CancelIdList) {
			if ("".equals(id))
				continue;
			MonthshopBasData monShopd = monthShopDRep.findOne(Long.parseLong(id));
			monShopd.setUsed(0);
			monShopd.setMonthTaskSub(null);
			MonthTaskSub oldTask = subTaskRep.findFirstByMonthsd_id(Long.parseLong(id));
			oldTList.add(oldTask);
			sTList.add(monShopd);
			set--;
		}
		// setTal10set
		// Method[] mets=mclass.getDeclaredMethods();
		Method m1 = mclass.getDeclaredMethod("setTal" + goal + "set", Integer.class);
		m1.invoke(monthTask, set);
		monthTaskrep.save(monthTask);
		if (sTList.size() > 0) {
			monthShopDRep.save(sTList);
		}
		if (oldTList.size() > 0)
			subTaskRep.delete(oldTList);

	}

	@Override
	public Map<String, Object> findTask(Map<String, Object> params, Pageable pageRequest) throws Exception {
		String month = params.get("EQ_taskMonth") == null ? DateUtil.getPreMonth(new Date(), 0)
				: params.get("EQ_taskMonth") + "";
		params.remove("EQ_taskMonth");
		String regionId = params.get("LK_regionId").toString();
		params.remove("LK_regionId");
		Set<String> regionSet = getSubShopRegion(regionId);
		Page<MonthTaskSub> data = subTaskRep.findAll(new Specification<MonthTaskSub>() {
			@Override
			public Predicate toPredicate(Root<MonthTaskSub> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				Join<MonthTaskSub, MonthshopBasData> leftJoin = root
						.join(root.getModel().getSingularAttribute("monthsd", MonthshopBasData.class), JoinType.LEFT);
				predicates.add(leftJoin.get("regionId").in(regionSet));
				predicates.add(cb.equal(leftJoin.get("month").as(String.class), month));
				createPedicateByMap(params, root, cb, predicates);
				return cb.and(predicates.toArray(new Predicate[] {}));
			}
		}, pageRequest);
		/*
		 * memberName":"章丘魅族手机专卖", "memberId":"12345", "goal":10, "done":11,
		 * "taskMonth":"2016-06", "delay":0};
		 * 
		 */
		Map<String, Object> dmap = new HashMap<String, Object>();
		List<MonthTaskSub> dlist = data.getContent();
		List<SubtaskVo> subtList = new ArrayList<SubtaskVo>();
		for (MonthTaskSub tasksub : dlist) {
			MonthshopBasData mtsbd = tasksub.getMonthsd();
			RegistData regData = mtsbd.getRegistData();
			SubtaskVo vo = new SubtaskVo(regData.getShopName(), regData.getId() + "", mtsbd.getMonth(),
					tasksub.getGoal(), tasksub.getDone(), tasksub.getDelay());
			subtList.add(vo);
		}
		dmap.put("totalPages", data.getTotalPages());
		dmap.put("data", subtList);
		dmap.put("code", "0");
		dmap.put("msg", "");
		return dmap;
	}

	/*
	 * {       "code": 0,   “msg”:””, “goal”:15, “done”:10,
	 * “shopName”:”章丘魅族手机专卖”, “visit”:[ {“day”:28,”action”:”送货”,”time”:”08:23”},
	 * {“day”:20,”action”:”送货”,”time”:”08:23”} ]
	 * 
	 * }
	 */
	@Override
	public Map<String, Object> findExecution(Long memberId) {
		String taskMonth = DateUtil.getPreMonth(new Date(), 0);
		MonthTaskSub mtaskSub = subTaskRep.findFirstByMonthsd_RegistData_IdAndMonthsd_Month(memberId, taskMonth);
		List<MonthTaskExecution> dlist = mtExecRepository.findByTaskmonthAndRegistData_idOrderByTimeDesc(taskMonth,
				memberId);
		Map<String, Object> dmap = new HashMap<String, Object>();
		dmap.put("goal", mtaskSub.getGoal());
		dmap.put("done", mtaskSub.getDone());
		dmap.put("shopName", mtaskSub.getMonthsd().getRegistData().getShopName());
		dmap.put("code", "0");
		dmap.put("msg", "");
		List<Map<String, String>> vlist = new ArrayList<Map<String, String>>();

		for (MonthTaskExecution mt : dlist) {
			Map<String, String> mtMap = new HashMap<String, String>();
			String sd = DateUtil.date2String(mt.getTime(), "yyyy-MM-dd HH:mm:dd");
			String[] timeArr = sd.split(" ");
			String day = timeArr[0].substring(8);
			String time = timeArr[1].substring(0, 5);
			mtMap.put("day", day);
			mtMap.put("time", time);
			mtMap.put("action", mt.getAction());
			vlist.add(mtMap);
		}
		dmap.put("visit", vlist);
		return dmap;
	}

	/**
	 * 根据其上次有效访问时间来判断其是否为有效访问
	 * 
	 * @param shopId
	 * @param action
	 */
	@Override
	public void saveExecution(Long shopId, String action) {
		String taskMonth = DateUtil.getPreMonth(new Date(), 0);
		MonthTaskSub mtaskSub = subTaskRep.findFirstByMonthsd_RegistData_IdAndMonthsd_Month(shopId, taskMonth);
		RegistData regd = registRep.findOne(shopId);
		MonthTaskExecution mtsExec = new MonthTaskExecution(regd, taskMonth, new Date(), action);
		mtExecRepository.save(mtsExec);
		/*
		 *  1.在"注册"的时候添加到店铺历史数据表里 
		 *  2.在完成任务时更新main表里的记录并更新sub表里记录
		 */
		if (action.equals("注册")) {
			RegistData r = registRep.findOne(shopId);
			MonthshopBasData shop = new MonthshopBasData(r.getRegion().getId(), 0, 0, taskMonth, 0, r, r.getSalesman());
			try {
				monthShopDRep.save(shop);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (null != mtaskSub) {
			Date lsTime = mtaskSub.getLastTime();
			if (!(DateUtil.date2String(lsTime)).equals(DateUtil.date2String(new Date()))) {
				if (mtaskSub.getGoal() <= mtaskSub.getDone() + 1) {
					mtaskSub.setFinish(1);
					MonthTask mainTask = mtaskSub.getMonthTask();
					int level = mtaskSub.getGoal();
					setDone(level, mainTask);
				} else {
					mtaskSub.setFinish(0);
				}
				mtaskSub.setDone(mtaskSub.getDone() + 1);
				// mtaskSub.setLastTime(new Date());
				subTaskRep.save(mtaskSub);
			}

		}
	}

	private void setDone(int level, MonthTask mt) {
		Class<? extends MonthTask> mclass = mt.getClass();
		String rate = null;
		try {
			Integer sum = getReflectInt(mclass.getDeclaredMethod("getTal" + level + "done").invoke(mt));
			mclass.getDeclaredMethod("setTal" + level + "done", Integer.class).invoke(mt, ++sum);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}
}

class SubtaskVo {
	private String shopName, shopId, taskMonth;
	private int goal, done, delay;

	public String getTaskMonth() {
		return taskMonth;
	}

	public void setTaskMonth(String taskMonth) {
		this.taskMonth = taskMonth;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public SubtaskVo(String shopName, String shopId, String taskMonth, int goal, int done, int delay) {
		super();
		this.shopName = shopName;
		this.shopId = shopId;
		this.taskMonth = taskMonth;
		this.goal = goal;
		this.done = done;
		this.delay = delay;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public SubtaskVo() {
		super();
	}

}
