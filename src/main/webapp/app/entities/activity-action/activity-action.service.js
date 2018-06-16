(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('ActivityAction', ActivityAction);

  ActivityAction.$inject = ['$resource'];

  function ActivityAction($resource) {
    var resourceUrl = 'api/activity-actions/:id';

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
