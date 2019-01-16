#!/usr/bin/env node
import { DeviceFarm } from 'aws-sdk';
import { promisify } from 'es6-promisify';
import shell from 'shelljs';

const deviceFarm = new DeviceFarm({ region: 'us-west-2' });

const run = async (): Promise<void> => {
    const exec = promisify(shell.exec);

    await exec('mvn clean package -DskipTests=true');

    const uploadAppResponse = await deviceFarm.createUpload({
        projectArn: 'arn:aws:devicefarm:us-west-2:170789063619:project:243ba094-1eca-4e6f-bc29-d7181eeeb45e',
        type: 'ANDROID_APP',
        name: 'testUploadCli.apk'
    }).promise();

    if (!uploadAppResponse.upload) {
        throw Error('Cannot create app upload');
    }

    await exec(`curl -T src/app-release-bitrise-signed.apk "${uploadAppResponse.upload.url}"`);

    const uploadTestsResponse = await deviceFarm.createUpload({
        projectArn: 'arn:aws:devicefarm:us-west-2:170789063619:project:243ba094-1eca-4e6f-bc29-d7181eeeb45e',
        type: 'APPIUM_JAVA_TESTNG_TEST_PACKAGE',
        name: 'testUploadCli.zip'
    }).promise();

    if (!uploadTestsResponse.upload) {
        throw Error('Cannot create app upload');
    }

    await exec(`curl -T target/zip-with-dependencies.zip "${uploadTestsResponse.upload.url}"`);

    const schedule = await deviceFarm.scheduleRun({
        appArn: uploadAppResponse.upload.arn,
        devicePoolArn: 'arn:aws:devicefarm:us-west-2:170789063619:devicepool:243ba094-1eca-4e6f-bc29-d7181eeeb45e/2b66dad2-7161-4e8d-907d-bfd07ff93b09',
        name: 'testUploadCli',
        projectArn: 'arn:aws:devicefarm:us-west-2:170789063619:project:243ba094-1eca-4e6f-bc29-d7181eeeb45e',
        test: {
            testPackageArn: uploadTestsResponse.upload.arn,
            type: 'APPIUM_JAVA_TESTNG'
        }
    }).promise();

    console.log(schedule);
}

run();
