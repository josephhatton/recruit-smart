(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('WorkStatus', WorkStatus);

  WorkStatus.$inject = ['$resource'];

  function WorkStatus($resource) {
    var resourceUrl = 'api/work-statuses/:id';

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
