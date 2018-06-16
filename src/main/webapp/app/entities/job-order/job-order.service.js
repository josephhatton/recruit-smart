(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('JobOrder', JobOrder);

  JobOrder.$inject = ['$resource'];

  function JobOrder($resource) {
    var resourceUrl = 'api/job-orders/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true},
      'get': {
        method: 'GET',
        transformResponse: function (data) {
          if (data) {
            data = angular.fromJson(data);
          }
          return data;
        }
      },
      'update': {method: 'PUT'}
    });
  }
})();
