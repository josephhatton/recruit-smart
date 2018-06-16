(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('ApplicantSearch', ApplicantSearch);

  ApplicantSearch.$inject = ['$resource'];

  function ApplicantSearch($resource) {
    var resourceUrl = 'api/_search/applicants/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
