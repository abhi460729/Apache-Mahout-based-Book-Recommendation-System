package bookmartapplication.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;

import bookmartapplication.main.utilities.Constants;
import net.sf.ehcache.config.CacheConfiguration;

@SpringBootApplication
@EnableCaching
public class BookMartApplication extends CachingConfigurerSupport {
	public static void main(String[] args) {
		SpringApplication.run(BookMartApplication.class, args);
	}
	
	
	  @Bean 
	  public net.sf.ehcache.CacheManager ehCacheManager() 
	  {
	  CacheConfiguration tenSecondsCache=new CacheConfiguration();
	  tenSecondsCache.setName(Constants.TEN_SECONDS_CACHE);
	  tenSecondsCache.setMemoryStoreEvictionPolicy(Constants.
	  MEMORY_STORE_EVICTION_POLICY);
	  tenSecondsCache.setMaxEntriesLocalHeap(Constants.MAX_CACHE_ENTRIES);
	  tenSecondsCache.setTimeToLiveSeconds(Constants.TEN_SECONDS_CACHE_TIME);
	  
	  CacheConfiguration twentySecondsCache=new CacheConfiguration();
	  twentySecondsCache.setName(Constants.TWENTY_SECONDS_CACHE);
	  twentySecondsCache.setMemoryStoreEvictionPolicy(Constants.
	  MEMORY_STORE_EVICTION_POLICY);
	  twentySecondsCache.setMaxEntriesLocalHeap(Constants.MAX_CACHE_ENTRIES);
	  twentySecondsCache.setTimeToLiveSeconds(Constants.TWENTY_SECONDS_CACHE_TIME);
	  
	  net.sf.ehcache.config.Configuration config= new net.sf.ehcache.config.Configuration(); 
	  config.addCache(tenSecondsCache);
	  config.addCache(twentySecondsCache);
	  
	  return net.sf.ehcache.CacheManager.newInstance(config); 
	  }
	  
	  @Bean
	  @Override 
	  public CacheManager cacheManager() 
	  { 
		  // TODO Auto-generated method
		  return new EhCacheCacheManager(ehCacheManager());
	  }
	 
	
	

}
