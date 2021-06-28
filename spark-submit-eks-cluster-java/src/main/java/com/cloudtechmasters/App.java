package com.cloudtechmasters;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
/**
 * Hello world!
 *
 */
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
public class App {
	public static void main(String[] args) {
		// System.out.println( "Hello World!" );
		SparkSession spark = SparkSession.builder().appName("JSubmittingApplications").getOrCreate();

		System.out.println("You are using Spark " + spark.version());
		spark.sparkContext().setLogLevel("ERROR");
		List<Row> list=new ArrayList<Row>();
		list.add(RowFactory.create("one"));
		list.add(RowFactory.create("two"));
		list.add(RowFactory.create("three"));
		list.add(RowFactory.create("four"));
		List<org.apache.spark.sql.types.StructField> listOfStructField=new ArrayList<org.apache.spark.sql.types.StructField>();
		listOfStructField.add(DataTypes.createStructField("test", DataTypes.StringType, true));
		StructType structType=DataTypes.createStructType(listOfStructField);
		Dataset<Row> data=spark.createDataFrame(list,structType);
		data.show();

		spark.stop();
	}
}
