(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('Activity', Activity);

  Activity.$inject = ['$resource', 'DateUtils'];

  function Activity($resource, DateUtils) {
    var resourceUrl = 'api/activities/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true},
      'get': {
        method: 'GET',
        transformResponse: function (data) {
          if (data) {
            data = angular.fromJson(data);
            data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
          }
          return data;
        }
      },
      'update': {method: 'PUT'}
    });
  }
})();
