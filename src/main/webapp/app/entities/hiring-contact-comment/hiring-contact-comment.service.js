(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('HiringContactComment', HiringContactComment);

  HiringContactComment.$inject = ['$resource'];

  function HiringContactComment($resource) {
    var resourceUrl = 'api/hiring-contact-comments/:id';

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
