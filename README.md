# auction_app_0.1

## Data Feeding 

As the user I want to provide the file with the data as a csv file with given & correct structure. The system should take the file every 15 seconds and load the data accordingly, to the DB. The already fed data should not be loaded again. The system could assume, that if the data from the range 0 to f.eg. 25 was fed during the load, the next load should take the data starting from the index 26, till the EOF.

Enjoy. :)

load-data.csv
Laptop,500,600
Smartphone,300,300
Headphones,100,100
Some new data,500,600
Some other data,300,300

DataFeedingJob
@Scheduled(fixedDelay=15000) // cron expression
public void dataFeedingJob() {
    dataFeedingService.feedData();
}

Extra points:
- the data should be validated in terms of the data types and fulfillment
- the job execution ratio should be parameterized
- the user could modify already loaded data - marking data as loaded should not be performed based on the index (f.eg. the user could remove some already loaded data, or move the rows etc.)