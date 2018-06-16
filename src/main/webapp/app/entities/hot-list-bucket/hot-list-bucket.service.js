(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('HotListBucket', HotListBucket);

  HotListBucket.$inject = ['$resource'];

  function HotListBucket($resource) {
    var resourceUrl = 'api/hot-list-buckets/:id';

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
