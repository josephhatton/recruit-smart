(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('JobOrderInternalComment', JobOrderInternalComment);

  JobOrderInternalComment.$inject = ['$resource'];

  function JobOrderInternalComment($resource) {
    var resourceUrl = 'api/job-order-internal-comments/:id';

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
