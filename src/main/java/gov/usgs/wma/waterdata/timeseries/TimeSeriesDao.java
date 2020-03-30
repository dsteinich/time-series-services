package gov.usgs.wma.waterdata.timeseries;

import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gov.usgs.wma.waterdata.collections.CollectionParams;

@Repository
public class TimeSeriesDao extends SqlSessionDaoSupport {

	private CollectionParams collectionsParams;

	@Autowired
	public TimeSeriesDao(SqlSessionFactory sqlSessionFactory, CollectionParams collectionsParams) {
		setSqlSessionFactory(sqlSessionFactory);
		this.collectionsParams = collectionsParams;
	}

	public String getTimeSeries(String featureId, String timeSeriesId) {
		return getTimeSeries(CollectionParams.DEFAULT_COLLECTION_ID, featureId, timeSeriesId);
	}

	public String getTimeSeries(String collectionId, String featureId, String timeSeriesId) {
		Map<String,Object> params = collectionsParams.buildParams(collectionId, featureId, timeSeriesId);
		return getSqlSession().selectOne("groundwaterDailyValue.getGeoJson", params);
	}

}
