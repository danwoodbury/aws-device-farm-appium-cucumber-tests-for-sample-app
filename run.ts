#!/usr/bin/env node
import { DeviceFarm } from 'aws-sdk';




const deviceFarm = new DeviceFarm({ region: 'us-west-2' });

deviceFarm.getUpload({
    arn: 'arn:aws:devicefarm:us-west-2:170789063619:upload:243ba094-1eca-4e6f-bc29-d7181eeeb45e/27e659c2-796b-4739-989f-370c91770b16'
}).promise().then(console.log);
