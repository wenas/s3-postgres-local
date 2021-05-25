package com.example;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class S3LocalTest {

  @Test
  void ローカルS3に接続する() throws IOException {

    AWSCredentials credentials = new BasicAWSCredentials("minioadmin", "minioadmin");

    AwsClientBuilder.EndpointConfiguration configuration =
        new AwsClientBuilder.EndpointConfiguration(
            "http://127.0.0.1:9000", Regions.AP_NORTHEAST_1.getName());

    // クレデンシャルとエンドポイントを書き換え
    final AmazonS3 s3 =
        AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(configuration)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();

    List<Bucket> buckets = s3.listBuckets();
    System.out.println("Your Amazon S3 buckets are:");
    for (Bucket b : buckets) {
      System.out.println("* " + b.getName());
    }

    ListObjectsV2Result result = s3.listObjectsV2("test-buckets", "dir1/");
    List<S3ObjectSummary> objects = result.getObjectSummaries();
    for (S3ObjectSummary os : objects) {
      System.out.println("* " + os.getKey());
    }

    s3.copyObject("test-buckets", "dir1/test.txt", "test-buckets", "dir2/test.txt");
  }
}
