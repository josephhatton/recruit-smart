(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('CompanyJob', CompanyJob);

  CompanyJob.$inject = ['$resource'];

  function CompanyJob($resource) {
    var resourceJobUrl = 'api/companies/job-order/:id';

    return $resource(resourceJobUrl, {}, {
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
