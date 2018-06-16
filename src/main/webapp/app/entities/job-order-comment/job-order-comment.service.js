(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('JobOrderComment', JobOrderComment);

  JobOrderComment.$inject = ['$resource'];

  function JobOrderComment($resource) {
    var resourceUrl = 'api/job-order-comments/:id';

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
