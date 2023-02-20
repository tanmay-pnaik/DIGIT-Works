// ignore_for_file: avoid_dynamic_calls

import 'dart:async';

import 'package:dio/dio.dart';
import 'package:works_shg_app/utils/constants.dart';

import '../../../models/init_mdms/global_config_model.dart';

class GetGlobalConfig {
  Future<GlobalConfigModel> getGlobalConfig() async {
    final dio = Dio();
    try {
      var response = await dio.get(
        Constants.devAssets,
        options: Options(
          headers: {
            'Access-Control-Allow-Origin': '*', // or specify allowed origins
            'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
            'Access-Control-Allow-Headers':
                'Origin, X-Requested-With, Content-Type, Accept',
          },
        ),
      );

      return GlobalConfigModel.fromJson(
        response.data,
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
