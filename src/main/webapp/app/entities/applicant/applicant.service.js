(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('Applicant', Applicant);

  Applicant.$inject = ['$resource'];

  function Applicant($resource) {
    var resourceUrl = 'api/applicants/:id';

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
