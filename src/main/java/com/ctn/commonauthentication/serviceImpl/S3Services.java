package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.service.IS3Services;
import com.ctn.commonauthentication.util.enums.FileUploadContentType;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class S3Services implements IS3Services {
    @Override
    public String getPreSignedUrl(String bucketName, String keyName) {
        return createPresignedGetUrl(bucketName , keyName);
    }

    @Override
    public String getPreSignedPutUrl(String bucketName, String keyName) {
        return createPresignedPutUrl(bucketName , keyName);
    }

    @Override
    public void uploadFile(String bucketName, String keyName, byte[] fileBytes , FileUploadContentType fileUploadContentType) throws Exception {

        try {
            S3Client s3Client = S3Client.builder()
                    .credentialsProvider(ProfileCredentialsProvider.create())
                    .region(Region.AP_NORTHEAST_1)
                    .build();
            // Meta data
            Map<String, String> metadata = new HashMap<>();
            //content type
            metadata.put("Content-Type", fileUploadContentType.getValue());
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .metadata(metadata)
                    .build();
            // Create a file path
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileBytes));
        }catch (Exception ex){
            throw ex;
        }
    }

    /* Create a pre-signed URL to download an object in a subsequent GET request. */
    private String createPresignedGetUrl(String bucketName, String keyName) {
        // Set the region for the S3Presigner
        try (S3Presigner presigner = S3Presigner.builder()
                .region(Region.AP_NORTHEAST_1)  // Set the region here
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build()) {

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .getObjectRequest(getObjectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
            return presignedRequest.url().toExternalForm();
        } catch (NoSuchBucketException bucketException) {
            throw new RuntimeException("Bucket not found");
        } catch (Exception exception) {
            throw exception;
        }
    }

    private String createPresignedPutUrl(String bucketName, String keyName) {
        // Set the region for the S3Presigner
        try (S3Presigner presigner = S3Presigner.builder()
                .region(Region.AP_NORTHEAST_1)  // Set the region here
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build()) {

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .putObjectRequest(putObjectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            return presignedRequest.url().toExternalForm();
        } catch (NoSuchBucketException bucketException) {
            throw new RuntimeException("Bucket not found");
        } catch (Exception exception) {
            throw exception;
        }
    }
}

